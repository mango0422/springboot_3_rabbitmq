package dev.mq.rabbit.controller;

import dev.mq.rabbit.dto.BaseResponseEntity;
import dev.mq.rabbit.dto.SendRequestDto;
import dev.mq.rabbit.dto.SendResponseDto;
import dev.mq.rabbit.service.RabbitmqService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndpointController {
  /**
   * RabbitMQ에 메시지를 전송하는 엔드포인트
   *
   * @param requestDto 메시지와 우선순위를 포함하는 DTO
   * @return 전송 결과 메시지
   */
  RabbitmqService rabbitmqService;

  public EndpointController(RabbitmqService rabbitmqService) {
    this.rabbitmqService = rabbitmqService;
  }

  @PostMapping("/send")
  public BaseResponseEntity<SendResponseDto> sendMessage(@RequestBody SendRequestDto request) {
    BaseResponseEntity<SendResponseDto> response =
        rabbitmqService.sendMessageAndReturnResponse(request);
    // Logic to send the message to RabbitMQ
    return response;
  }
}
