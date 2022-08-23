package com.mungta.user.dto;

import lombok.NoArgsConstructor;

import com.mungta.user.AbstractEvent;
import com.mungta.user.model.UserEntity;
import com.mungta.user.model.Status;
import com.mungta.user.model.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends AbstractEvent{

  private String userId;
  private String userPassword;
  private String userMailAddress;
  private String userName;
  private String userPhoto;
  private String userTeamName;
  private String userGender;
  private String driverYn;
  private String settlementUrl;
  private String carType;
  private String carNumber;
  private Long penaltyCount;
  private Status status;
  private UserType userType;
  private String token;

  // Entity -> Dto
  public UserDto(final UserEntity user){
    this.userId = user.getUserId();
    this.userPassword = user.getUserPassword();
    this.userMailAddress = user.getUserMailAddress();
    this.userName = user.getUserName();
    this.userPhoto = user.getUserPhoto();
    this.userTeamName = user.getUserTeamName();
    this.userGender = user.getUserGender();
    this.driverYn = user.getDriverYn();
    this.settlementUrl = user.getUserName();
    this.carType = user.getCarType();
    this.carNumber = user.getCarNumber();
    this.penaltyCount = user.getPenaltyCount();
    this.userType = user.getUserType();
    this.status = user.getStatus();
  }
  // Dto -> Entity
  public static UserEntity toEntity(final UserDto userDto){
    return UserEntity.builder()
               .userId(userDto.getUserId())
               .userPassword(userDto.getUserPassword())
               .userMailAddress(userDto.getUserMailAddress())
               .userName(userDto.getUserName())
               .userPhoto(userDto.getUserPhoto())
               .userTeamName(userDto.getUserTeamName())
               .userGender(userDto.getUserGender())
               .driverYn(userDto.getDriverYn())
               .settlementUrl(userDto.getSettlementUrl())
               .carType(userDto.getCarType())
               .carNumber(userDto.getCarNumber())
               .penaltyCount(userDto.getPenaltyCount())
               .status(userDto.getStatus())
               .userType(userDto.getUserType())
               .build();
  }
}
