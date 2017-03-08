/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.web.rest.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
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
@RequestMapping("/boot.elven.tech/web/rest/test")
public class TestController {

    /** 日志记录器 **/
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("hello")
    @ResponseBody
    public String hello() {
        logger.info("start hello...");
        return "hello world!!!";
    }

    @RequestMapping("redis/set/{key}/{value}")
    @ResponseBody
    public String redisSet(@PathVariable String key, @PathVariable String value){
        logger.info("start redis/set/{}/{}", key, value);
        redisTemplate.opsForValue().set(key, value);
        return "success";
    }

    @RequestMapping("redis/get/{key}")
    @ResponseBody
    public String redisGet(@PathVariable String key){
        logger.info("start redis/get/{}", key);
        String value = (String) redisTemplate.opsForValue().get(key);
        return key+":"+value;
    }
}