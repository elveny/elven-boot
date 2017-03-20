/**
 * msxf.com Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.batch.tutorial.jobs.job1;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;

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
@EnableBatchProcessing
@Configuration
public class OneStep {

//    @Bean(name = "firstStepOfOneStep")
    public Step firstStep(StepBuilderFactory stepBuilderFactory, @Qualifier("firstStepOfOneStepItemReader") ItemReader<?> itemReader){

        return stepBuilderFactory.get("firstStep").chunk(1000).reader(itemReader).build();
    }

//    @Bean(name = "firstStepOfOneStepItemReader")
//    @StepScope
    public ItemReader<?> firstStepItemReader(@Value("#{jobParameters['fileName']}") String filePath,
                                             @Value("#{stepExecutionContext['linesToSkip']}") Integer lines,
                                             @Value("#{stepExecutionContext['lineCount']}") Integer lineCount) throws FileNotFoundException {
        FlatFileItemReader<?> reader = new FlatFileItemReader<Object>();

        reader.setResource(new FileSystemResource(ResourceUtils.getFile(filePath)));

        reader.setEncoding(Charset.forName("utf-8").name());
        reader.setLinesToSkip(lines);

        return reader;
    }




}