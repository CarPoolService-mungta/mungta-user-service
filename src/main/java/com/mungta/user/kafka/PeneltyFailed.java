package com.mungta.user.kafka;

import lombok.Getter;

@Getter
public class PeneltyFailed extends AbstractEvent {

  private final String accusedMemberId;
  private final String partyId;

  public PeneltyFailed(String accusedMemberId, String partyId) {
      super();
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
