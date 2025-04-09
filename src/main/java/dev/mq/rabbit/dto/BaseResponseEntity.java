package dev.mq.rabbit.dto;

public record BaseResponseEntity<T>(String message, Integer code, T data) {
  public BaseResponseEntity(ApiResponseType type, T data) {
    this(type.getMessage(), type.getCode(), data);
  }
}
