/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.plugins.shedlock;

import net.javacrumbs.shedlock.core.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Filename ScheduledService.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-11-3 上午2:11</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Component
public class ShedlockService {

    private final static Logger logger = LoggerFactory.getLogger(ShedlockService.class);

    private static final int ONE_MIN = 1 * 60 * 1000;

    public void scheduledTask1() {
        logger.info("開始ScheduledService.scheduledTask1");
        // do something
        jdbcTemplateLock();
        logger.info("結束ScheduledService.scheduledTask1");
    }

    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(name = "jdbcTemplateScheduledTask1", lockAtLeastFor = ONE_MIN, lockAtMostFor = ONE_MIN)
    public void jdbcTemplateLock() {
        logger.info("start jdbcTemplateLock");
        // do something
        logger.info("end jdbcTemplateLock");
    }
}