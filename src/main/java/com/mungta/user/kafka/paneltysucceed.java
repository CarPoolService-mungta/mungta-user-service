package com.mungta.user.kafka;

import lombok.Getter;

@Getter
public class paneltysucceed extends AbstractEvent {

  private final String accusedMemberId;

  public paneltysucceed(String accusedMemberId, String payload) {
      super();
      this.accusedMemberId = accusedMemberId;
  }

  @Override
  public String toString() {
      return "AccusationCompleted{" +
              "eventType='" + eventType + '\'' +
              ", timestamp='" + timestamp + '\'' +
              ", accusedMemberId='" + accusedMemberId + '\'' +
              '}';
  }

}


// 회원 시스템으로 신고당한 사람 ID 전송

