package com.myd.helloworld.jms;

import com.myd.helloworld.util.ServerMetaUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.MessageTransformer;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.jms.JmsPoolConnectionFactoryProperties;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 10:17
 * @Description: https://www.cnblogs.com/duanxz/p/7493276.html @Configuration
 *
 * spring 5.*版本已封装好 无需配置工厂和监听容器
 * 但是自定义和可以添加很多自己需要的属性
 */
@EnableJms
@EnableConfigurationProperties(ActiveMQProperties.class)
@Configuration
public class JmsConfiguration {
    //连接工厂配置
    @Configuration
    static class ConnectionFactoryConfiguration{
        @Value("${app.name}")
        private String appName;

        @Bean
        @ConditionalOnMissingBean //保证这个bean只会被注册一个
        public MessageTransformer messageTransformer(){
            return new MessageTransformer() {
                @Override
                public Message producerTransform(Session session, MessageProducer messageProducer, Message message) throws JMSException {
                    message.setStringProperty(JmsConstants.MESSAGE_SENDER_APP,appName);
                    message.setStringProperty(JmsConstants.MESSAGE_SENDER_IP, ServerMetaUtil.getLocalIP());
                    return message;
                }

                @Override
                public Message consumerTransform(Session session, MessageConsumer messageConsumer, Message message) throws JMSException {
                    return message;
                }
            };
        }

        @Primary  //接口有不同实现时 优先使用
        @Bean(name={"producerConnectionFactory","queueConsumerConnectionFactory","jmsConnectionFactory"},destroyMethod = "stop")
        //prefix为配置文件中的前缀, spring.activemq.pool
        //name为配置的名字 enable
        //havingValue是与配置的值对比值,当两个值相同返回true,配置类生效 true
        //是否启用连接池
        //spring.activemq.pool.enabled=true
        @ConditionalOnProperty(prefix = "spring.activemq.pool",name = "enable",havingValue = "true")
        public PooledConnectionFactory producerPoolConnectionFactory(final ActiveMQProperties properties){
            PooledConnectionFactory factory = new PooledConnectionFactory();
            return this.createPoolConnectionFactory(properties.getPool(),this.createNativeConnectionFactory(properties));
        }


        @Primary
        //matchIfMissing=true  不强制enable一定有值为false
        @ConditionalOnProperty(prefix = "spring.activemq.pool",name = "enable",havingValue = "false",matchIfMissing = true)
        @Bean(name = {"producerConnectionFactory","queueConsumerConnectionFactory","jmsConnectionFactory"})
        public ConnectionFactory producerConnectionFactory(final ActiveMQProperties properties){
            return this.createNativeConnectionFactory(properties);
        }


        @Bean(name = "topicConsumerConnectionFactory")
        public ConnectionFactory topicConsumerConnectionFactory(final ActiveMQProperties properties){
            return this.createNativeConnectionFactory(properties);
        }

        /**
         * 创建工厂连接池
         * @param pool
         * @param connectionFactory
         * @return  CachingConnectionFactory <====>PooledConnectionFactory
         */
        private PooledConnectionFactory createPoolConnectionFactory(JmsPoolConnectionFactoryProperties pool, ActiveMQConnectionFactory connectionFactory) {
            final PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
            pooledConnectionFactory.setConnectionFactory(connectionFactory);
            pooledConnectionFactory.setMaxConnections(pool.getMaxConnections());
            pooledConnectionFactory.setIdleTimeout((int)pool.getIdleTimeout().getSeconds());
            pooledConnectionFactory.setExpiryTimeout(pool.getTimeBetweenExpirationCheck().toMillis());
            return pooledConnectionFactory;
        }

        /**
         * 创建连接工厂
         * @param properties
         * @return
         */
        private ActiveMQConnectionFactory createNativeConnectionFactory(final ActiveMQProperties properties) {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            connectionFactory.setBrokerURL(properties.getBrokerUrl());
            connectionFactory.setUserName(properties.getUser());
            connectionFactory.setPassword(properties.getPassword());
            connectionFactory.setTransformer(this.messageTransformer());
            return connectionFactory;
        }


    }
    //监听容器配置
    @Configuration
    static class ListenerContainerFactoryConfiguration{

        @Value("${app.name}")
        private String appName;

        @Bean(name = {"queueListenerContainerFactory", "jmsListenerContainerFactory"})
        public JmsListenerContainerFactory queueListenerContainerFactory(@Qualifier("queueConsumerConnectionFactory") ConnectionFactory connectionFactory, final DefaultJmsListenerContainerFactoryConfigurer configurer){
            DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
            configurer.configure(factory,connectionFactory);
            /**
             * 对列消息
             */
            factory.setPubSubDomain(false);
            /**
             * 不带事务的签收方式
             */
            factory.setSessionTransacted(false);
            /**
             * 应答模式
             * AUTO(1),
             * CLIENT(2),
             * DUPS_OK(3);
             **/
            factory.setSessionAcknowledgeMode(JmsProperties.AcknowledgeMode.CLIENT.getMode());
            return factory;
        }

        @Bean(name = "itemChangeTopicListenerContainerFactory")
        public JmsListenerContainerFactory itemChangeTopicListenerContainerFactory(@Qualifier("topicConsumerConnectionFactory") final ConnectionFactory connectionFactory,
                                                                                   final DefaultJmsListenerContainerFactoryConfigurer configurer){
            return this.createTopicJmsListenerContainerFactory(configurer,connectionFactory,"itemChangeListener");
        }

        private DefaultJmsListenerContainerFactory createTopicJmsListenerContainerFactory(final DefaultJmsListenerContainerFactoryConfigurer configurer,
                                                                                          final ConnectionFactory connectionFactory,
                                                                                          final String uniqueClientId){
            DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
            configurer.configure(factory,connectionFactory);
            factory.setClientId(String.join(":",appName,uniqueClientId));
            factory.setSessionAcknowledgeMode(JmsProperties.AcknowledgeMode.CLIENT.getMode());
            factory.setPubSubDomain(true);
            factory.setSessionTransacted(false);
            //??
            factory.setSubscriptionDurable(true);
            return factory;
        }


    }

    /**
     * 模板初始化
     */
    @Bean(name = "topicTemplate")
    public JmsTemplate topTemplate(@Qualifier("producerConnectionFactory") final ConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        //设置我们的jmsTemplate是队列还是发布订阅类型消息，isPubSubDomain可以在application.preperties中配置spring.jms.pub-sub-domain，默认其为false即我队列模式
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }

    @Bean(name = "queueTemplate")
    public JmsTemplate queueTemplate(@Qualifier("producerConnectionFactory") final ConnectionFactory connectionFactory){
        return new JmsTemplate(connectionFactory);
    }
}
