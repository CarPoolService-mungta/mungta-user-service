package com.mungta.user.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
//import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name ="USER", uniqueConstraints = {@UniqueConstraint(columnNames = "userMailAddress")})
public  class UserEntity extends BaseEntity {

    @Id
    @NotNull(message = "id는 필수 값입니다.")
	private String userId;
    @Email
    @NotNull
	private String userMailAddress;
    @NotNull
	private String userPassword;
    @Column(nullable = false)
	private String userName;

    // 사진 저장 시작
	private String userPhoto;
    // @NotEmpty
    // private String original_file_name;
    // @NotEmpty
    // private String stored_file_path;
    // private long file_size;
    // 사진 저장 끝
    private String userTeamName;
	private String userGender;

   // private String refreshToken; //gateway 에 저장하는것 같기도..

    @NotNull
    private String driverYn;
	private String settlementUrl;
	private String carType;
	private String carNumber;

    @Builder.Default
    private Long penaltyCount =0L;

    @Builder.Default
	private Status status = Status.ACTIVATED;

    @Builder.Default
    private UserType userType = UserType.CUSTOMER;


    public static UserEntity of(UserEntity user) {
      return UserEntity.builder()
                       .userId(user.getUserId())
                       .userPassword(user.getUserPassword())
                       .userMailAddress(user.getUserMailAddress())
                       .userName(user.getUserName())
                       .userPhoto(user.getUserPhoto())
                       .userTeamName(user.getUserTeamName())
                       .userGender(user.getUserGender())
                       .driverYn(user.getDriverYn())
                       .settlementUrl(user.getSettlementUrl())
                       .carType(user.getCarType())
                       .carNumber(user.getCarNumber())
                       .penaltyCount(user.getPenaltyCount())
                       .status(user.getStatus())
                       .userType(user.getUserType())
                       .build();
      }
}

