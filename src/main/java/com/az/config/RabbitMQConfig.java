package com.az.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EMAIL_EXCHANGE    = "nida.email.exchange";
    public static final String NOTIFY_EXCHANGE   = "nida.notify.exchange";

    public static final String EMAIL_VERIFY_QUEUE    = "nida.email.verify";
    public static final String EMAIL_RESET_QUEUE     = "nida.email.reset";
    public static final String EMAIL_NOTIFY_QUEUE    = "nida.email.notify";

    public static final String EMAIL_VERIFY_KEY  = "email.verify";
    public static final String EMAIL_RESET_KEY   = "email.reset";
    public static final String EMAIL_NOTIFY_KEY  = "email.notify";

    @Bean
    public TopicExchange emailExchange()
    {
        return new TopicExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public TopicExchange notifyExchange() {
        return new TopicExchange(NOTIFY_EXCHANGE);
    }

    @Bean
    public Queue emailVerifyQueue() {
        return QueueBuilder.durable(EMAIL_VERIFY_QUEUE).build();
    }

    @Bean
    public Queue emailResetQueue() {
        return QueueBuilder.durable(EMAIL_RESET_QUEUE).build();
    }

    @Bean
    public Queue emailNotifyQueue() {
        return QueueBuilder.durable(EMAIL_NOTIFY_QUEUE).build();
    }

    @Bean
    public Binding emailVerifyBinding() {
        return BindingBuilder
                .bind(emailVerifyQueue())
                .to(emailExchange())
                .with(EMAIL_VERIFY_KEY);
    }

    @Bean
    public Binding emailResetBinding() {
        return BindingBuilder
                .bind(emailResetQueue())
                .to(emailExchange())
                .with(EMAIL_RESET_KEY);
    }

    @Bean
    public Binding emailNotifyBinding() {
        return BindingBuilder
                .bind(emailNotifyQueue())
                .to(emailExchange())
                .with(EMAIL_NOTIFY_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}