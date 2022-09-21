package com.mungta.user.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbstractEvent {

  @JsonProperty("eventType")
  String eventType;

  @JsonProperty("timestamp")
  String timestamp;

  @JsonProperty("accusedMemberId")
  String userId;

//   @JsonProperty("accusedMemberId")
//   String userId;
}
