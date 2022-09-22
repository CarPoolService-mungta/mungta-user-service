package com.mungta.user.kafka;

import lombok.Getter;

@Getter
public class PeneltySucceed extends AbstractEvent {

  private final String accusedMemberId;
  private final String partyId;

  public PeneltySucceed(String accusedMemberId, String partyId) {
      super();
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
