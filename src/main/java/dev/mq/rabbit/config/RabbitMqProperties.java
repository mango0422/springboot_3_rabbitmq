package dev.mq.rabbit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMqProperties {
  private String host;
  private int port;
  private String username;
  private String password;
  private String virtualHost;

  private QueueProperties queue = new QueueProperties();
  private ExchangeProperties exchange = new ExchangeProperties();
  private RoutingProperties routing = new RoutingProperties();

  public static class QueueProperties {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  public static class ExchangeProperties {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  public static class RoutingProperties {
    private String key;

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }
  }

  // getters/setters
  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getVirtualHost() {
    return virtualHost;
  }

  public void setVirtualHost(String virtualHost) {
    this.virtualHost = virtualHost;
  }

  public QueueProperties getQueue() {
    return queue;
  }

  public ExchangeProperties getExchange() {
    return exchange;
  }

  public RoutingProperties getRouting() {
    return routing;
  }
}
