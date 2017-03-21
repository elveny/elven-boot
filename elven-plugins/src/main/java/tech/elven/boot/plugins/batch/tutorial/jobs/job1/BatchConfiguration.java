/**
 * msxf.com Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.batch.tutorial.jobs.job1;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
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
import tech.elven.boot.plugins.data.jpa.entity.User;
import tech.elven.boot.plugins.data.jpa.repository.UserRepository;

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
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        reader.setResource(new ClassPathResource("tech/elven/boot/plugins/batch/tutorial/jobs/job1/sample-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "firstName", "lastName" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<Person, User> processor() {
        return person -> {
            String firstName = person.getFirstName().toUpperCase();
            String lastName = person.getLastName().toUpperCase();

            return new User(firstName+","+lastName, 20);
        };
    }

    @Bean
    public JpaItemWriter<User> jpaWriter(){
        JpaItemWriter<User> writer = new JpaItemWriter<User>();
        writer.setEntityManagerFactory(entityManagerFactory);

        return writer;
    }

    @Bean
    public ItemWriter<User> writer(){
        return items -> userRepository.save(items);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Bean
    public ItemReader<User> reader1() {
        AbstractPaginatedDataItemReader<User> reader = new AbstractPaginatedDataItemReader<User>() {
            @Override
            protected Iterator<User> doPageRead() {
                return userRepository.findAll(new PageRequest(page, pageSize)).getContent().iterator();
            }
        };
        reader.setPageSize(2);
        reader.setName("reader1");

        /*ItemReader<User> reader = new AbstractPaginatedDataItemReader<User>(){
            @Override
            protected Iterator<User> doPageRead() {
                return userRepository.findAll(new PageRequest(page, pageSize)).getContent().iterator();
            }
        };*/
        return reader;
    }

    public ItemProcessor<User, Person> processor1(){
        return user -> {

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
        FlatFileItemWriter<Person> writer = new FlatFileItemWriter<Person>();
        writer.setEncoding("utf-8");
        writer.setLineSeparator("\r\n");
        writer.setResource(new PathResource("out/tech/elven/boot/plugins/batch/tutorial/jobs/job1/sample-data1.csv"));
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

        return writer;
    }

//    public ItemWriter<Person> writer1() {
//        FlatFileItemWriter<Person> writer = new FlatFileItemWriter<Person>();
//        writer.setEncoding("utf-8");
//        writer.setResource(new PathResource("tech/elven/boot/plugins/batch/tutorial/jobs/job1/sample-data1.csv"));
//        DelimitedLineAggregator<Person> delimitedLineAggregator = new DelimitedLineAggregator<Person>();
//        delimitedLineAggregator.setDelimiter("|");
//        String[] names = {"firstName","lastName"};
//        BeanWrapperFieldExtractor<Person> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<Person>();
//        beanWrapperFieldExtractor.setNames(names);
//        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
//        writer.setLineAggregator(delimitedLineAggregator);
//        return writer;
//    }
    // end::readerwriterprocessor[]

    // tag::step[]
    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<Person, User> chunk(2)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    public Step step1(){
        return stepBuilderFactory.get("step1")
                .<User, Person> chunk(2)
                .reader(reader1())
                .processor(processor1())
                .writer(writer1())
                .build();
    }

    // end::step[]

    // tag::job[]
    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step())
                .next(step1())
                .end()
                .build();
    }
    // end::job[]

    // tag::joblistener[]
    @Bean
    public JobExecutionListener listener(){
        return new JobExecutionListenerSupport(){
            @Override
            public void beforeJob(JobExecution jobExecution) {
                super.beforeJob(jobExecution);
                logger.info("Id::"+jobExecution.getId());
                logger.info("JobId::"+jobExecution.getJobId()+", CreateTime::"+jobExecution.getCreateTime()+", EndTime::"+jobExecution.getEndTime());
                logger.info("JobId::"+jobExecution.getJobId()+", StartTime::"+jobExecution.getStartTime()+", EndTime::"+jobExecution.getEndTime());
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                super.afterJob(jobExecution);

                if (jobExecution.getStatus() == BatchStatus.COMPLETED){
                    logger.info("!!! JOB FINISHED! Time to verify the results");

                    List<User> users = userRepository.findAll();

                    for (User user : users){
                        logger.info("Found <" + JSON.toJSONString(user) + "> in the database.");
                    }

                }
            }
        };
    }
    // end::joblistener[]
}