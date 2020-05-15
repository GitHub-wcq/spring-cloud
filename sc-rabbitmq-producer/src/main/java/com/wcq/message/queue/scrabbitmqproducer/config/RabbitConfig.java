package com.wcq.message.queue.scrabbitmqproducer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类功能描述：<br>
 * Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 * Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 * Queue:消息的载体,每个消息都会被投到一个或多个队列。
 * Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 * Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 * vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 * Producer:消息生产者,就是投递消息的程序.
 * Consumer:消息消费者,就是接受消息的程序.
 * Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 */
@Configuration
public class RabbitConfig {

    public static final String FANOUT_QUEUE_NAME = "test_fanout_queue";
    public static final String FANOUT_QUEUE_NAME_1 = "test_fanout_queue_1";
    public static final String FANOUT_EXCHANGE = "test_fanout_exchange";

    public static final String DIRECT_QUEUE_NAME = "test_direct_queue";
    public static final String DIRECT_EXCHANGE = "test_direct_exchang";
    public static final String DIRECT_ROUTING_KEY = "test_direct_routingkey";

    public static final String TOPIC_QUEUE_NAME = "test_topic_queue";
    public static final String TOPIC_EXCHANGE = "test_topic_exchange";
    public static final String TOPIC_ROUTING_KEY = "test.topic.*";

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue createFountQueue(){
        return new Queue(FANOUT_QUEUE_NAME);
    }
    @Bean
    public Queue createFountQueue1(){
        return new Queue(FANOUT_QUEUE_NAME_1);
    }
    @Bean
    public Queue createDirectQueue(){
        return new Queue(DIRECT_QUEUE_NAME);
    }
    @Bean
    public Queue createTopicQueue(){
        return new Queue(TOPIC_QUEUE_NAME);
    }

    /**
     * 创建exchange
     * @return
     */
    @Bean
    public FanoutExchange createFanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }
    @Bean
    public DirectExchange createDirectExchange(){
        return new DirectExchange(DIRECT_EXCHANGE);
    }
    @Bean
    public TopicExchange createTopicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * 绑定交换机
     * @return
     */
    @Bean
    Binding bindingFanout(){
        return BindingBuilder.bind(createFountQueue()).to(createFanoutExchange());
    }
    @Bean
    Binding bindingFanout1(){
        return BindingBuilder.bind(createFountQueue1()).to(createFanoutExchange());
    }
    @Bean
    Binding bindingDirect(){
        return BindingBuilder.bind(createDirectQueue()).to(createDirectExchange()).with(DIRECT_ROUTING_KEY);
    }
    @Bean
    Binding bindingTopic(){
        return BindingBuilder.bind(createTopicQueue()).to(createTopicExchange()).with(TOPIC_ROUTING_KEY);
    }
}
