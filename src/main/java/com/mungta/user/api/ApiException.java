package com.mungta.user.api;

import lombok.Getter;

public class ApiException extends RuntimeException {
  @Getter
  private ApiStatus apiStatus;

  public ApiException(ApiStatus apiStatus) {
      super(apiStatus.getHttpStatus()+":"+apiStatus.getMessage()+"("+apiStatus.getCode()+")");
      this.apiStatus = apiStatus;
  }

  public ApiException(String apiStatus) {
      super(apiStatus);
  }
}
