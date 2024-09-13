package br.edu.ifpb.pdist_back.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value(value = "${broker.queue.post.like}")
    private String postLikeQueue;

    @Value(value = "${broker.queue.comment.like}")
    private String commentLikeQueue;

    @Bean
    public Queue postLikeQueue() {
        return new Queue(postLikeQueue, true);
    }

    @Bean
    public Queue commentLikeQueue() {
        return new Queue(commentLikeQueue, true);
    }
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setConcurrentConsumers(8);
        factory.setMaxConcurrentConsumers(16);

        //factory.setBatchSize(65000);

        factory.setPrefetchCount(50);
        return factory;
    }
}
