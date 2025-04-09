package dev.mq.rabbit.dto;

public enum ApiResponseType {
  SUCCESS(0, "성공"),
  MESSAGE_SEND_FAIL(501, "메시지 전송 실패"),
  FAILURE(502, "실패"),
  MESSAGE_SEND_SUCCESS(200, "메시지 전송 성공"),
  MESSAGE_RECEIVE_SUCCESS(201, "메시지 수신 성공"),
  ;

  ApiResponseType(int i, String string) {
    this.code = i;
    this.message = string;
  }

  private final int code;
  private final String message;

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
