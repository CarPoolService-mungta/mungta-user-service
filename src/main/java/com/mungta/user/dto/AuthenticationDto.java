package com.mungta.user.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;

import com.mungta.user.model.AuthenticationEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto {

	private String userMailAddress;
	private String authNumber;
  private String possibleYn;

  // Entity -> Dto
  public AuthenticationDto(final AuthenticationEntity auth){
    this.userMailAddress = auth.getUserMailAddress();
    this.authNumber      = auth.getAuthNumber();
    this.possibleYn      = "-";
  }

  // Dto -> Entity
  public static AuthenticationEntity toEntity(final AuthenticationDto authDto){
    return AuthenticationEntity.builder()
                         .userMailAddress(authDto.getUserMailAddress())
                         .authNumber(authDto.getAuthNumber())
                         .build();
  }
}
