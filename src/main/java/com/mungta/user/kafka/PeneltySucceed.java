package com.mungta.user.kafka;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;

@Getter
public class PeneltySucceed extends AbstractEvent {

  // private final String accusedMemberId;
  // private final String partyId;

  public PeneltySucceed(String accusedMemberId, String partyId) {
    super();
    this.eventType = this.getClass().getSimpleName();
    this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    this.accusedMemberId = accusedMemberId;
    this.partyId = partyId;
}


  @Override
  public String toString() {

      return "PeneltySucceed{" +
             "eventType='" + eventType + '\'' +
             ", timestamp='" + timestamp + '\'' +
             ", accusedMemberId='" + accusedMemberId + '\'' +
             ", partyId='" + partyId + '\'' +
            '}';
  }
}