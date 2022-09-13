package com.mungta.user.dto;

import com.mungta.user.model.UserEntity;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {

  private String userId;
  private String userPassword;
  // private String userMailAddress;
  // private Status status;
  // private UserType userType;
  // private String token;

  public UserLoginDto (UserEntity user){
    this.userId          = user.getUserId();
    this.userPassword    = user.getUserPassword();
    // this.userMailAddress = user.getUserMailAddress();
    // this.status = user.getStatus();
    // this.userType = user.getUserType();
  }

  public static UserEntity toEntity(final UserLoginDto userLoginDto){
    return UserEntity.builder()
               .userId(userLoginDto.getUserId())
               .userPassword(userLoginDto.getUserPassword())
              //  .userMailAddress(userLoginDto.getUserMailAddress())
              //  .status(userLoginDto.getStatus())
              //  .userType(userLoginDto.getUserType())
               .build();
  }
}
