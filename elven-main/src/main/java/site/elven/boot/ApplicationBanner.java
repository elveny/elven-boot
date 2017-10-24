/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * @Filename ApplicationBanner.java
 *
 * @description Banner
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-3-6 下午11:17</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class ApplicationBanner implements Banner {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationBanner.class);

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        logger.info("///////////////////////////elven.tech///////////////////////////");
        logger.info("//                    ELVEN-BOOT START                        //");
        logger.info("///////////////////////////////END//////////////////////////////");

    }
}