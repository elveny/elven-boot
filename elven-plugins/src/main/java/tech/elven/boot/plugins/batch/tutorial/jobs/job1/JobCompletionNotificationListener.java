/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.batch.tutorial.jobs.job1;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.elven.boot.plugins.data.jpa.entity.User;
import tech.elven.boot.plugins.data.jpa.repository.UserRepository;

import java.util.List;

/**
 * @Filename JobCompletionNotificationListener.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-3-21 上午12:02</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Autowired
    private UserRepository userRepository;

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
}