package com.mungta.user.dto;

import java.util.List;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T>  {

  private String error;
  private List<T> data;

}
