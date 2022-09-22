package com.mungta.user.kafka;

import lombok.Getter;

@Getter
public class AccusationCompleted extends AbstractEvent {
  private final String accusedMemberId;
  private final String partyId;

  public AccusationCompleted(String accusedMemberId, String partyId) {
    this.accusedMemberId = accusedMemberId;
    this.partyId = partyId;
  }

}
