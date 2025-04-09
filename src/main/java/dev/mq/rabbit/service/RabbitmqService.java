package dev.mq.rabbit.service;

import dev.mq.rabbit.config.RabbitConnectionInfoProvider;
import dev.mq.rabbit.config.RabbitMqProperties;
import dev.mq.rabbit.dto.ApiResponseType;
import dev.mq.rabbit.dto.BaseResponseEntity;
import dev.mq.rabbit.dto.PriorityEnum;
import dev.mq.rabbit.dto.SendRequestDto;
import dev.mq.rabbit.dto.SendResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqService {
  private final Logger logger = LoggerFactory.getLogger(RabbitmqService.class);
  private final RabbitTemplate rabbitTemplate;
  private final RabbitConnectionInfoProvider connectionInfoProvider;
  private final RabbitMqProperties properties;

  public RabbitmqService(
      RabbitConnectionInfoProvider rabbitConnectionInfoProvider,
      RabbitTemplate rabbitTemplate,
      RabbitMqProperties properties) {
    this.connectionInfoProvider = rabbitConnectionInfoProvider;
    this.rabbitTemplate = rabbitTemplate;
    this.properties = properties;
  }

  public void sendMessage(String message, PriorityEnum priority) {
    if (message == null || message.isEmpty()) {
      logger.warn("메시지는 null 또는 빈 문자열일 수 없습니다.");
    }
    rabbitTemplate.convertAndSend(
        properties.getExchange().getName(),
        properties.getRouting().getKey(),
        message,
        msg -> {
          msg.getMessageProperties().setPriority(priority.getValue());
          return msg;
        });

    logger.info(
        "메시지 전송 완료: exchange={}, routingKey={}, priority={}, message={}",
        properties.getExchange().getName(),
        properties.getRouting().getKey(),
        priority,
        message);
  }

  public BaseResponseEntity<SendResponseDto> sendMessageAndReturnResponse(SendRequestDto request) {
    try {
      sendMessage(request.message(), request.priority());

      SendResponseDto response =
          new SendResponseDto(
              request.message(),
              request.priority(),
              connectionInfoProvider.getConnectedHost(),
              properties.getExchange().getName(),
              properties.getRouting().getKey());

      return new BaseResponseEntity<>(ApiResponseType.MESSAGE_SEND_SUCCESS, response);
    } catch (Exception e) {
      logger.error("메시지 전송 중 오류 발생: {}", e.getMessage());
      return new BaseResponseEntity<>(ApiResponseType.MESSAGE_SEND_FAIL, null);
    } finally {
      logger.info("메시지 전송 완료");
    }
  }
}
