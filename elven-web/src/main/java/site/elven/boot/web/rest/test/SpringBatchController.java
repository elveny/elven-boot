/**
 * elven.site Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.web.rest.test;

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
import site.elven.boot.common.enums.ResultStatus;
import site.elven.boot.plugins.batch.quickstart.DemoBatchConfiguration;

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
@RequestMapping("/boot.elven.site/web/rest/test/batch")
public class SpringBatchController extends BaseController {
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

    @RequestMapping("hello")
    public String hello() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        Job job = demoBatchConfiguration.buildJob(oneStep, twoStep);

        JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder().toJobParameters());

        logger.info("jobExecution.status::::"+jobExecution.getStatus().toString());

        return ResultStatus.SUCCESS.code();
    }
}