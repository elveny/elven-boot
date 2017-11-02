/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.web.rest.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.boot.plugins.shedlock.ShedlockService;

/**
 * @Filename ShedlockController.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-11-3 上午2:26</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@RestController
@RequestMapping("/boot.elven.site/web/rest/test/shedlock")
public class ShedlockController {

    /** 日志记录器 **/
    private static final Logger logger = LoggerFactory.getLogger(ShedlockController.class);

    @Autowired
    private ShedlockService shedlockService;
    /**
     * home
     * @return
     */
    @RequestMapping()
    public String home(){
        return "shedlock:home";
    }

    @RequestMapping("jdbc")
    public String jdbc() {
        logger.info("start hello...");
        shedlockService.scheduledTask1();
        return "SUCCESS";
    }
}