package com.mungta.user.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
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

    @Lob
    private byte[] userPhoto;
    @Column(nullable = false)
    private String userFileName;
    @Column(nullable = false)
    private String userFileOriName;

    private long   userFileSize;
    private String fileExtension;
    private String userTeamName;
	private String userGender;

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
    
}

