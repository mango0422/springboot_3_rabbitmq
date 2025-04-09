package dev.mq.rabbit.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
  private final RabbitMqProperties properties;

  RabbitMqConfig(RabbitMqProperties properties) {
    this.properties = properties;
  }

  @Value("${spring.rabbitmq.queue.name}")
  private String queueName;

  @Value("${spring.rabbitmq.exchange.name}")
  private String exchangeName;

  @Value("${spring.rabbitmq.routing.key}")
  private String routingKey;

  @Bean
  public Queue queue() {
    return QueueBuilder.durable(properties.getQueue().getName())
        .withArgument("x-max-priority", 10) // Optional: 우선순위 큐
        .build();
  }

  @Bean
  public DirectExchange exchange() {
    return new DirectExchange(properties.getExchange().getName(), true, false);
  }

  @Bean
  public Binding binding(Queue queue, DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(properties.getRouting().getKey());
  }

  @Bean
  public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }
}
