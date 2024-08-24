package com.trustyourfeet.overhang.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String AUTH_EXCHANGE = "auth-exchange";
    public static final String IDENTITY_EXCHANGE = "identity-exchange";

    public static final String AUTH_ACCOUNT_REGISTRATION_ROUTING = "auth.event.account.registration";

    public static final String CREATE_IDENTITY_PROFILE_QUEUE = "create-identity-profile-queue";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange identityExchange() {
        return new TopicExchange(IDENTITY_EXCHANGE);
    }

    @Bean
    public TopicExchange authExchange() {
        return new TopicExchange(AUTH_EXCHANGE);
    }

    @Bean
    public Queue createIdentityProfileQueue() {
        return new Queue(CREATE_IDENTITY_PROFILE_QUEUE, true);
    }

    @Bean
    public Binding createIdentityProfileBinding(Queue createIdentityProfileQueue, TopicExchange authExchange) {
        return BindingBuilder.bind(createIdentityProfileQueue).to(authExchange).with(AUTH_ACCOUNT_REGISTRATION_ROUTING);
    }
}
