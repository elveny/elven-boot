/**
 * msxf.com Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author qiusheng.wu
 * @Filename RabbitMQListener.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/3/9 18:40</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Component
public class RabbitMQListener {
    private final static Logger logger = LoggerFactory.getLogger(RabbitMQListener.class);

    /**
     * 步骤三：监听并接收消息
     * @param content
     * @param channel
     * @param messageSource
     * @throws IOException
     */
    @RabbitListener(queues = MqConstant.DEMO_QUEUE, containerFactory = "rabbitListenerContainerFactory")
    public void processMessage(String content, Channel channel, Message messageSource) throws IOException {
        // ...
        logger.info("收到消息："+content);

        channel.basicAck(messageSource.getMessageProperties().getDeliveryTag(), false);
    }

}