package dev.mq.rabbit.dto;

public record SendResponseDto(
    String message,
    PriorityEnum priority,
    String connectedHost,
    String exchange,
    String routingKey) {}
