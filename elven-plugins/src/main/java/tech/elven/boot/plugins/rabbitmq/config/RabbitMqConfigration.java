/**
 * msxf.com Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.rabbitmq.config;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;

/**
 * rabbitmq配置信息
 * @author qiusheng.wu
 * @Filename RabbitMqConfigration.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/3/9 18:09</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
@EnableRabbit
public class RabbitMqConfigration {

    /**
     * 配置RabbitAdmin
     * @param connectionFactory
     * @return
     */
    @Bean
    public AmqpAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * 配置MessageConverter
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        //Kroty转换器没测通
        //return new KryoMessageConvertor();
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 配置ConnectionFactory
     * @param rabbitProperties
     * @return
     * @throws Exception
     */
    @Bean
    public ConnectionFactory connectionFactory(RabbitProperties rabbitProperties) throws Exception {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());
        connectionFactory.setAddresses(rabbitProperties.getAddresses());
        return connectionFactory;
    }

    /**
     * 配置SimpleRabbitListenerContainerFactory
     * @param connectionFactory
     * @param messageConverter
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;

    }

    /**
     * 配置RabbitTemplate
     * @param connectionFactory
     * @param messageConverter
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    /**
     * 消息发布者：
       步骤一：申明Exchange（TopicExchange、FanoutExchange、DirectExchange等）
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public TopicExchange topicExchange(AmqpAdmin rabbitAdmin) {
        // 方式一：直接创建
        // TopicExchange topicExchange = new TopicExchange(MqConstant.DEMO_EXCHANGE_TOPIC, true, false);
        // 方式二：通过ExchangeBuilder创建
        TopicExchange topicExchange = (TopicExchange) ExchangeBuilder.topicExchange(MqConstant.DEMO_EXCHANGE_TOPIC).durable().build();
        rabbitAdmin.declareExchange(topicExchange);
        return topicExchange;
    }

    /**
     * 消息发布者：
       步骤一：申明Exchange（TopicExchange、FanoutExchange、DirectExchange等）
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange(AmqpAdmin rabbitAdmin) {
        // 方式一：直接创建
        // FanoutExchange fanoutExchange = new FanoutExchange(MqConstant.DEMO_EXCHANGE_FANOUT,true,false);
        // 方式二：通过ExchangeBuilder创建
        FanoutExchange fanoutExchange = (FanoutExchange) ExchangeBuilder.fanoutExchange(MqConstant.DEMO_EXCHANGE_FANOUT).durable().build();
        rabbitAdmin.declareExchange(fanoutExchange);
        return fanoutExchange;
    }

    /**
     * 消息接收者：
       步骤一：申明队列
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Queue demoQueue(AmqpAdmin rabbitAdmin) {
        // 方式一：直接创建队列
        // Queue demoQueue = new Queue(MqConstant.DEMO_QUEUE,true);
        // 方式二：QueueBuilder创建队列
        Queue demoQueue = QueueBuilder.nonDurable(MqConstant.DEMO_QUEUE).build();
        rabbitAdmin.declareQueue(demoQueue);
        return demoQueue;
    }

    /**
     * 消息接收者：
       步骤二：绑定Exchange
     * @param demoQueue
     * @param topicExchange
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Binding bindingExchangePayCoreQueue(Queue demoQueue, TopicExchange topicExchange, AmqpAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(demoQueue).to(topicExchange).with(MqConstant.DEMO_ROUTING_KEY);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }
}