/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Filename ControllerMonitor.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-3-8 上午1:03</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Aspect
@Component
public class ControllerMonitor {

    /** 日志记录器 **/
    private static final Logger logger = LoggerFactory.getLogger(ControllerMonitor.class);

    @Before("execution(* tech.elven.boot.web..*Controller.*(..))")
    public void logControllerBeforeAccess(JoinPoint joinPoint) {
        logger.info("Start: {}", joinPoint);
    }

    @AfterReturning("execution(* tech.elven.boot.web..*Controller.*(..))")
    public void logControllerAfterAccess(JoinPoint joinPoint) {
        logger.info("Completed: {}", joinPoint);
    }
}