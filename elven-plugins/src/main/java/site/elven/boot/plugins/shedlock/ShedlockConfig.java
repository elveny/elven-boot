/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.plugins.shedlock;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.ScheduledLockConfiguration;
import net.javacrumbs.shedlock.spring.ScheduledLockConfigurationBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.Duration;

/**
 * @Filename ShedlockConfig.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-11-3 上午2:19</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Configuration
public class ShedlockConfig {

    @Bean
    public LockProvider lockProvider(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplateLockProvider(dataSource);
    }

    @Bean
    public ScheduledLockConfiguration taskScheduler(LockProvider lockProvider) {
        return ScheduledLockConfigurationBuilder
                .withLockProvider(lockProvider)
                .withPoolSize(10)
                .withDefaultLockAtMostFor(Duration.ofSeconds(1))
                .build();
    }
}