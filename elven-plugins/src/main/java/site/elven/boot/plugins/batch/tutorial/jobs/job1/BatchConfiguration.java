/**
 * msxf.com Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.plugins.batch.tutorial.jobs.job1;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.data.domain.PageRequest;
import site.elven.boot.plugins.data.jpa.entity.User;
import site.elven.boot.plugins.data.jpa.repository.UserRepository;

import javax.persistence.EntityManagerFactory;
import java.util.Iterator;
import java.util.List;


/**
 * @author qiusheng.wu
 * @Filename OneStep.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/3/17 17:28</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class BatchConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserRepository userRepository;


    // tag::readerwriterprocessor[]
    @Bean
    public ItemReader<Person> reader() {
        logger.info("reader::start::");
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        reader.setResource(new ClassPathResource("site/elven/boot/plugins/batch/tutorial/jobs/job1/sample-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "firstName", "lastName" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
        logger.info("reader::end::");
        return reader;
    }

    @Bean
    public ItemProcessor<Person, User> processor() {
        logger.info("processor::start::");
        return person -> {
            logger.info("processor::doing::");
            String firstName = person.getFirstName().toUpperCase();
            String lastName = person.getLastName().toUpperCase();

            return new User(firstName+","+lastName, 20);
        };
    }

    @Bean
    public JpaItemWriter<User> jpaWriter(){
        logger.info("jpaWriter::start::");
        JpaItemWriter<User> writer = new JpaItemWriter<User>();
        writer.setEntityManagerFactory(entityManagerFactory);
        logger.info("jpaWriter::end::");
        return writer;
    }

    @Bean
    public ItemWriter<User> writer(){
        logger.info("writer::start::");
        return items -> userRepository.save(items);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Bean
    public ItemReader<User> reader1() {
        logger.info("reader1::start::");
        AbstractPaginatedDataItemReader<User> reader = new AbstractPaginatedDataItemReader<User>() {
            @Override
            protected Iterator<User> doPageRead() {
                logger.info("reader1::doing::");
                return userRepository.findAll(new PageRequest(page, pageSize)).getContent().iterator();
            }
        };
        reader.setPageSize(10);
        reader.setName("reader1");

        /*ItemReader<User> reader = new AbstractPaginatedDataItemReader<User>(){
            @Override
            protected Iterator<User> doPageRead() {
                return userRepository.findAll(new PageRequest(page, pageSize)).getContent().iterator();
            }
        };*/
        logger.info("reader1::end::");
        return reader;
    }

    public ItemProcessor<User, Person> processor1(){
        logger.info("processor1::start::");
        return user -> {
            logger.info("processor1::doing::");

            long id = user.getId();
            String name = user.getName();
            String firstName = name;
            String lastName = name;
            if(name.contains(",")){
                firstName = name.split(",")[0];
                lastName = name.split(",")[1];
            }

            firstName = id + "." + firstName;
            return new Person(firstName, lastName);
        };
    }

    public ItemWriter<Person> writer1(){
        logger.info("writer1::start::");

        FlatFileItemWriter<Person> writer = new FlatFileItemWriter<Person>();
        writer.setEncoding("utf-8");
        writer.setLineSeparator("\r\n");
        writer.setResource(new PathResource("out/site/elven/boot/plugins/batch/tutorial/jobs/job1/sample-data1.csv"));
        writer.setAppendAllowed(true);
        writer.setShouldDeleteIfExists(true);

        FormatterLineAggregator<Person> formatterLineAggregator = new FormatterLineAggregator<Person>();
        formatterLineAggregator.setFormat("%-20s%-20s");
        String[] names = {"lastName", "firstName"};
        BeanWrapperFieldExtractor<Person> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<Person>();
        beanWrapperFieldExtractor.setNames(names);
        formatterLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
        writer.setLineAggregator(formatterLineAggregator);

        /*DelimitedLineAggregator<Person> delimitedLineAggregator = new DelimitedLineAggregator<Person>();
        delimitedLineAggregator.setDelimiter("|");
        String[] names = {"firstName","lastName"};
        BeanWrapperFieldExtractor<Person> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<Person>();
        beanWrapperFieldExtractor.setNames(names);
        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
        writer.setLineAggregator(delimitedLineAggregator);*/
        logger.info("writer1::end::");

        return writer;
    }

//    public ItemWriter<Person> writer1() {
//        FlatFileItemWriter<Person> writer = new FlatFileItemWriter<Person>();
//        writer.setEncoding("utf-8");
//        writer.setResource(new PathResource("site/elven/boot/plugins/batch/tutorial/jobs/job1/sample-data1.csv"));
//        DelimitedLineAggregator<Person> delimitedLineAggregator = new DelimitedLineAggregator<Person>();
//        delimitedLineAggregator.setDelimiter("|");
//        String[] names = {"firstName","lastName"};
//        BeanWrapperFieldExtractor<Person> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<Person>();
//        beanWrapperFieldExtractor.setNames(names);
//        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
//        writer.setLineAggregator(delimitedLineAggregator);
//        return writer;
//    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Bean
    public ItemReader<User> reader2() {
        logger.info("reader2::start::");
        AbstractPaginatedDataItemReader<User> reader = new AbstractPaginatedDataItemReader<User>() {
            @Override
            protected Iterator<User> doPageRead() {
                logger.info("reader1::doing::");
                Iterator<User> results = userRepository.findAll(new PageRequest(page, pageSize)).getContent().iterator();
                while (results.hasNext()){
                    userRepository.delete(results.next());
                }
                return results;
            }
        };
        reader.setPageSize(10);
        reader.setName("reader2");

        logger.info("reader2::end::");
        return reader;
    }

    public ItemProcessor<User, User> processor2(){
        logger.info("processor2::start::");
        return user -> {
            logger.info("processor2::doing::");

            userRepository.delete(user.getId());

            return user;
        };
    }

    public ItemWriter<User> writer2(){
        logger.info("writer2::start::");

        FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();
        writer.setEncoding("utf-8");
        writer.setLineSeparator("\r\n");
        writer.setResource(new PathResource("out/site/elven/boot/plugins/batch/tutorial/jobs/job1/sample-data2.csv"));
        writer.setAppendAllowed(true);
        writer.setShouldDeleteIfExists(true);

        FormatterLineAggregator<User> formatterLineAggregator = new FormatterLineAggregator<User>();
        formatterLineAggregator.setFormat("%-20s%-20s");
        String[] names = {"id", "name", "age", "version"};
        BeanWrapperFieldExtractor<User> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<User>();
        beanWrapperFieldExtractor.setNames(names);
        formatterLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
        writer.setLineAggregator(formatterLineAggregator);

        logger.info("writer2::end::");

        return writer;
    }


    // end::readerwriterprocessor[]

    // tag::step[]
    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .listener(stepExecutionListener())
                .listener(itemReadListener())
                .listener(itemProcessListener())
                .listener(itemWriteListener())
                .listener(chunkListener())
                .<Person, User> chunk(2)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    public Step step1(){
        return stepBuilderFactory.get("step1")
                .listener(stepExecutionListener())
                .listener(itemReadListener())
                .listener(itemProcessListener())
                .listener(itemWriteListener())
                .listener(chunkListener())
                .<User, Person> chunk(2)
                .reader(reader1())
                .processor(processor1())
                .writer(writer1())
                .build();
    }

    public Step step2(){
        return stepBuilderFactory.get("step2")
                .listener(stepExecutionListener())
                .listener(itemReadListener())
                .listener(itemProcessListener())
                .listener(itemWriteListener())
                .listener(chunkListener())
                .<User, User> chunk(2)
                .reader(reader2())
                .processor(processor2())
                .writer(writer2())
                .build();
    }

    // end::step[]

    // tag::job[]
    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener())
                .flow(step())
                .next(step1())
                .next(step2())
                .end()
                .build();
    }
    // end::job[]

    // tag::jobExecutionListener[]
    @Bean
    public JobExecutionListener jobExecutionListener(){
        return new JobExecutionListenerSupport(){
            @Override
            public void beforeJob(JobExecution jobExecution) {
                super.beforeJob(jobExecution);
                logger.info("Listener::JobName::"+jobExecution.getJobInstance().getJobName()+"::before");
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                super.afterJob(jobExecution);
                logger.info("Listener::JobName::"+jobExecution.getJobInstance().getJobName()+"::after");
            }
        };
    }

    @Bean
    public StepExecutionListener stepExecutionListener(){
        return new StepExecutionListener(){

            @Override
            public void beforeStep(StepExecution stepExecution) {
                logger.info("Listener::StepName::"+stepExecution.getStepName()+"::before");
            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                logger.info("Listener::StepName::"+stepExecution.getStepName()+"::after");
                return stepExecution.getExitStatus();
            }
        };
    }

    @Bean
    public ItemReadListener itemReadListener(){
        return new ItemReadListener(){

            @Override
            public void beforeRead() {
                logger.info("Listener::ItemRead::::before");
            }

            @Override
            public void afterRead(Object item) {
                logger.info("Listener::ItemRead::::after");
            }

            @Override
            public void onReadError(Exception ex) {
                logger.info("Listener::ItemRead::::ReadError");
            }
        };
    }

    @Bean
    public ItemProcessListener itemProcessListener(){
        return new ItemProcessListener(){
            @Override
            public void beforeProcess(Object item) {
                logger.info("Listener::ItemProcess::"+JSON.toJSONString(item)+"::before");
            }

            @Override
            public void afterProcess(Object item, Object result) {
                logger.info("Listener::ItemProcess::"+JSON.toJSONString(item)+"::after");
            }

            @Override
            public void onProcessError(Object item, Exception e) {
                logger.info("Listener::ItemProcess::"+JSON.toJSONString(item)+"::ProcessError");
            }
        };
    }

    @Bean
    public ItemWriteListener itemWriteListener(){
        return new ItemWriteListener(){
            @Override
            public void beforeWrite(List items) {
                logger.info("Listener::ItemWrite::"+JSON.toJSONString(items)+"::before");
            }

            @Override
            public void afterWrite(List items) {
                logger.info("Listener::ItemWrite::"+JSON.toJSONString(items)+"::after");
            }

            @Override
            public void onWriteError(Exception exception, List items) {
                logger.info("Listener::ItemWrite::"+JSON.toJSONString(items)+"::WriteError");
            }
        };
    }

    @Bean
    public ChunkListener chunkListener(){
        return new ChunkListener(){
            @Override
            public void beforeChunk(ChunkContext context) {
                logger.info("Listener::Chunk::"+context+"::before");
            }

            @Override
            public void afterChunk(ChunkContext context) {
                logger.info("Listener::Chunk::"+context+"::after");
            }

            @Override
            public void afterChunkError(ChunkContext context) {
                logger.info("Listener::Chunk::"+context+"::ChunkError");
            }
        };
    }
    // end::jobExecutionListener[]
}