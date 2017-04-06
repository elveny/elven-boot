/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.web.rest.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.elven.boot.common.enums.ResultStatus;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

/**
 * @Filename WebMagicController.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-4-7 上午12:36</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@RestController
@RequestMapping("/boot.elven.tech/web/rest/test/webmagic")
public class WebMagicController {
    /** 日志记录器 **/
    private static final Logger logger = LoggerFactory.getLogger(WebMagicController.class);

    /**
     * home
     * @return
     */
    @RequestMapping()
    public String home(){
        return "home";
    }

    @RequestMapping("githubRepo")
    public String githubRepo(){
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).start();
        return ResultStatus.SUCCESS.code();
    }
}