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
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import tech.elven.boot.plugins.data.jpa.entity.User;
import tech.elven.boot.plugins.data.jpa.repository.UserRepository;

import javax.persistence.EntityManagerFactory;
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
    // end::readerwriterprocessor[]

    // tag::step[]
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, User> chunk(2)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    // end::step[]

    // tag::job[]
    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step1())
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