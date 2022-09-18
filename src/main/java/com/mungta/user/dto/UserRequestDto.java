package com.mungta.user.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.mungta.user.AbstractEvent;
import com.mungta.user.model.UserEntity;
import com.mungta.user.model.Status;
import com.mungta.user.model.UserType;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto extends AbstractEvent implements Serializable {

  private String userId;
  private String userPassword;
  private String userMailAddress;
  private String userName;
  private String userFileName;
  private String userFileOriName;
  private String userFileUrl;
  private long   userFileSize;
  private String userTeamName;
  private String userGender;
  private String driverYn;
  private String settlementUrl;
  private String carType;
  private String carNumber;
  private Long   penaltyCount;
  private Status status;
  private UserType userType;
  private MultipartFile profileImg;

  // Entity -> Dto
  public UserRequestDto(final UserEntity user){
    this.userId          = user.getUserId();
    this.userPassword    = user.getUserPassword();
    this.userMailAddress = user.getUserMailAddress();
    this.userName        = user.getUserName();
    this.userFileName    = user.getUserFileName ();
    this.userFileOriName = user.getUserFileOriName();
    //this.userFileUrl   = user.getUserFileUrl();
    this.userFileSize    = user.getUserFileSize();
    this.userTeamName    = user.getUserTeamName();
    this.userGender      = user.getUserGender();
    this.driverYn        = user.getDriverYn();
    this.settlementUrl   = user.getSettlementUrl();
    this.carType         = user.getCarType();
    this.carNumber       = user.getCarNumber();
    this.penaltyCount    = user.getPenaltyCount();
    this.userType        = user.getUserType();
    this.status          = user.getStatus();
  }
  // Dto -> Entity
  public static UserEntity toEntity(final UserRequestDto userRequestDto){
    return UserEntity.builder()
               .userId(userRequestDto.getUserId())
               .userPassword(userRequestDto.getUserPassword())
               .userMailAddress(userRequestDto.getUserMailAddress())
               .userName(userRequestDto.getUserName())
               //.userFileUrl(userRequestDto.getUserFileUrl())
               .userFileSize(userRequestDto.getUserFileSize())
               .userTeamName(userRequestDto.getUserTeamName())
               .userGender(userRequestDto.getUserGender())
               .driverYn(userRequestDto.getDriverYn())
               .settlementUrl(userRequestDto.getSettlementUrl())
               .carType(userRequestDto.getCarType())
               .carNumber(userRequestDto.getCarNumber())
               .penaltyCount(userRequestDto.getPenaltyCount())
               .status(userRequestDto.getStatus())
               .userType(userRequestDto.getUserType())
               .build();
  }
}
