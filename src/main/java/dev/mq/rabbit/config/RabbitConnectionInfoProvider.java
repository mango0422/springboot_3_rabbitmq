package dev.mq.rabbit.config;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RabbitConnectionInfoProvider {
  private final ConnectionFactory connectionFactory;

  RabbitConnectionInfoProvider(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  public String getConnectedHost() {
    try {
      Connection connection = connectionFactory.createConnection();

      return connection.getDelegate().getAddress().getHostName();
    } catch (Exception e) {
      return "unknown";
    }
  }
}
