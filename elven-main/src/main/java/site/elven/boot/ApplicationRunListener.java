/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Filename ApplicationRunListener.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-3-6 下午11:21</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class ApplicationRunListener implements SpringApplicationRunListener{

    private final static Logger logger = LoggerFactory.getLogger(ApplicationBanner.class);

    public ApplicationRunListener(SpringApplication application, String[] args) {
        Thread.currentThread().setName("ELVEN-BOOT");
        application.setBanner(new ApplicationBanner());
    }

    @Override
    public void starting() {
        logger.info("started");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        logger.info("environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        logger.info("contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        logger.info("contextLoaded");
    }

    @Override
    public void finished(ConfigurableApplicationContext context, Throwable exception) {
        logger.info("finished");
    }
}