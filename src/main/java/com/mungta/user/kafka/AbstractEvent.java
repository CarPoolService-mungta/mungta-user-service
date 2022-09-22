package com.mungta.user.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;


@Getter
@Setter
@NoArgsConstructor
public class AbstractEvent {

  @JsonProperty("eventType")
  String eventType;

  @JsonProperty("timestamp")
  String timestamp;

  @JsonProperty("accusedMemberId")
  String accusedMemberId;

  @JsonProperty("partyId")
  String partyId;
}
