package com.mungta.user.dto;

import lombok.NoArgsConstructor;

import com.mungta.user.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
  private String userId;
  private String userMailAddress;
  private byte[] userPhoto;
  private String fileExtension;

  // Entity -> Dto
  public UserResponseDto(final UserEntity user){
    this.userId          = user.getUserId();
    this.userMailAddress = user.getUserMailAddress();
    this.userPhoto       = user.getUserPhoto();
    this.fileExtension   = user.getFileExtension();
    //this.userPassword    = user.getUserPassword();
    // this.userName        = user.getUserName();
    // this.userFileName    = user.getUserFileName ();
    // this.userFileOriName = user.getUserFileOriName();
    //this.userFileUrl   = user.getUserFileUrl();
    // this.userFileSize    = user.getUserFileSize();
    // this.userTeamName    = user.getUserTeamName();
    // this.userGender      = user.getUserGender();
    // this.driverYn        = user.getDriverYn();
    // this.settlementUrl   = user.getSettlementUrl();
    // this.carType         = user.getCarType();
    // this.carNumber       = user.getCarNumber();
    // this.penaltyCount    = user.getPenaltyCount();
    // this.userType        = user.getUserType();
    // this.status          = user.getStatus();
  }
}
