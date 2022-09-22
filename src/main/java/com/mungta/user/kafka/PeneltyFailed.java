package com.mungta.user.kafka;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;

@Getter
public class PeneltyFailed extends AbstractEvent {

  // private final String accusedMemberId;
  // private final String partyId;

  public PeneltyFailed(String accusedMemberId, String partyId) {
      super();
      this.eventType = this.getClass().getSimpleName();
      this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      this.accusedMemberId = accusedMemberId;
      this.partyId = partyId;
  }

  @Override
  public String toString() {
      return "PeneltyFailed{" +
              "eventType='" + eventType + '\'' +
              ", timestamp='" + timestamp + '\'' +
              ", accusedMemberId='" + accusedMemberId + '\'' +
              ", partyId='" + partyId + '\'' +
              '}';
  }
}
