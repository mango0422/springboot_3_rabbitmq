// ./src/main/java/dev/mq/rabbit/dto/PriorityEnum.java
package dev.mq.rabbit.dto;

import java.util.Arrays;

public enum PriorityEnum {
  LOW(0),
  MEDIUM(5),
  HIGH(9);

  private final int value;

  PriorityEnum(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public static PriorityEnum fromValue(int value) {
    return Arrays.stream(values())
        .filter(p -> p.value == value)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid priority: " + value));
  }
}
