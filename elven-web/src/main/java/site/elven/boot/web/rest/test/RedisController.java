/**
 * elven.site Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.web.rest.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.boot.common.enums.ResultStatus;

/**
 * redis测试Controller
 * @author qiusheng.wu
 * @Filename RedisController.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/3/9 18:28</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@RestController
@RequestMapping("/boot.elven.site/web/rest/test/redis")
public class RedisController extends BaseController {
    /** 日志记录器 **/
    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    /** redis访问模板 **/
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("set/{key}/{value}")
    public String set(@PathVariable String key, @PathVariable String value){
        logger.info("start redis/set/{}/{}", key, value);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
        return ResultStatus.SUCCESS.code();
    }

    @RequestMapping("get/{key}")
    public String get(@PathVariable String key){
        logger.info("start redis/get/{}", key);
        String value = (String) redisTemplate.opsForValue().get(key);
        return key+":"+value;
    }

}