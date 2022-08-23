package com.mungta.user.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name ="AUTHENTICATION", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class AuthenticationEntity  extends BaseEntity {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String userMailAddress;
	private String authNumber;
	private String limitTime;

  @Builder.Default
  private String useYn ="Y" ;

    public static AuthenticationEntity of(AuthenticationEntity auth) {
      return AuthenticationEntity.builder()
                                .id(auth.getId())
                                .userMailAddress(auth.getUserMailAddress())
                                .authNumber(auth.getAuthNumber())
                                .limitTime(auth.getLimitTime())
                                .useYn(auth.getUseYn())
                                .build();
      }
}
