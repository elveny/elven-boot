/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.web.rest.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公用测试Controller
 * @Filename TestController.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-2-21 下午11:28</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@RestController
@RequestMapping("/boot.elven.tech/web/rest/test/test")
public class TestController {

    /** 日志记录器 **/
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * home
     * @return
     */
    @RequestMapping()
    public String home(){
        return "home";
    }

    @RequestMapping("hello")
    public String hello() {
        logger.info("start hello...");
        return "hello world!!!";
    }

}