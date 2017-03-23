/**
 * msxf.com Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.web.rest.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.elven.boot.common.enums.ResultStatus;
import tech.elven.boot.plugins.batch.quickstart.DemoBatchConfiguration;

/**
 * @author qiusheng.wu
 * @Filename SpringBatchController.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/3/10 18:18</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@RestController
@RequestMapping("/boot.elven.tech/web/rest/test/batch")
public class SpringBatchController {
    /** 日志记录器 **/
    private static final Logger logger = LoggerFactory.getLogger(SpringBatchController.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private DemoBatchConfiguration demoBatchConfiguration;

    @Autowired
    private Step oneStep;

    @Autowired
    private Step twoStep;

    /**
     * home
     * @return
     */
    @RequestMapping()
    public String home(){
        return "home";
    }

    @RequestMapping("hello")
    public String hello() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        Job job = demoBatchConfiguration.buildJob(oneStep, twoStep);

        JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder().toJobParameters());

        logger.info("jobExecution.status::::"+jobExecution.getStatus().toString());

        return ResultStatus.SUCCESS.code();
    }
}