package dev.mq.rabbit.dto;

public record SendRequestDto(String message, PriorityEnum priority) {}
