/**
 * msxf.com Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.web.rest.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * rabbitmq测试Controller
 * @author qiusheng.wu
 * @Filename RabbitmqController.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/3/9 18:27</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@RestController
@RequestMapping("/boot.elven.tech/web/rest/test/rabbitmq")
public class RabbitmqController {
    /** 日志记录器 **/
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqController.class);

    /** rabbitmq访问模板 **/
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /** rabbitAdmin **/
    @Autowired
    private AmqpAdmin rabbitAdmin;

    /**
     * 向消息队列中发送主题为DEMO_EXCHANGE_TOPIC的信息
     * @param content
     * @return
     */
    @RequestMapping("send/{content}")
    public String send(@PathVariable String content){
        rabbitTemplate.convertAndSend(MqConstant.DEMO_EXCHANGE_TOPIC, MqConstant.DEMO_ROUTING_KEY, content);
        return "SUCCESS";
    }

    @RequestMapping("delete/{type}/{name}")
    public String delete(@PathVariable String type, @PathVariable String name){

        boolean deleteFlag = false;
        rabbitAdmin.deleteQueue(name);

        rabbitAdmin.deleteExchange(name);

        return "success?"+deleteFlag;
    }
}