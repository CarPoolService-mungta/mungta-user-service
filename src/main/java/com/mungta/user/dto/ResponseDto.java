package com.mungta.user.dto;

import com.mungta.user.api.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto {
    private final Integer errorCode;
    private final String message;

    private ResponseDto(ApiStatus apiStatus) {
        this.errorCode = apiStatus.getCode();
        this.message   = apiStatus.getMessage();
    }

    public static ResponseDto of(ApiStatus apiStatus) {
        return new ResponseDto(apiStatus);
    }

}
