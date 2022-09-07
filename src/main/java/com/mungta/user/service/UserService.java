package com.mungta.user.service;

import com.mungta.user.dto.Token;
import com.mungta.user.dto.UserDto;
import com.mungta.user.model.Status;
import com.mungta.user.model.UserEntity;
import com.mungta.user.model.UserRepository;
import com.mungta.user.model.UserType;
import com.mungta.user.api.ApiException;
import com.mungta.user.api.ApiStatus;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
  private final TokenProvider tokenProvider;

	@Autowired
  private final PasswordEncoder passwordEncoder;


	//사용자정보 조회
	@Transactional
	public UserDto getUser (final String userId) {
		UserEntity results = userRepository.findByUserId(userId)
	                     	.orElseThrow(()-> new ApiException(ApiStatus.NOT_EXIST_INFORMATION));
		log.debug("################ UserService getUser OUTPUT : "+ToStringBuilder.reflectionToString(results));
		return new UserDto(results);
  }

	//사용자정보 등록
	@Transactional
  //public Token createUser (@Valid final UserEntity user) {
	public UserEntity  createUser (@Valid final UserEntity user) {
		//토큰 변수 생성
		//Token issuedToken = new Token();
		//Email 중복 체크
		if(userRepository.existsByUserMailAddress(user.getUserMailAddress())) {
			log.warn("Email already exists {}", user.getUserMailAddress());
			throw new ApiException(ApiStatus.DUPLICATED_INFORMATION);
		}
		try{
			//비밀번호 암호화
			user.setUserPassword(encodePassword(user.getUserPassword()));
			//토큰 생성
			//issuedToken=tokenProvider.createToken(user);
			//user.setRefreshToken(issuedToken.getRefreshToken());
			//사용자정보 저장
			userRepository.save(user);

		} catch(Exception e){
			log.error("error created user",user.getUserId(),e);
			new ApiException(ApiStatus.UNEXPECTED_ERROR);
		}
    return null; //issuedToken;
	}

	//사용자정보 변경
	@Transactional
	public UserEntity updateUser (@Valid final UserEntity user) {
		//해당 아이디 조회
		UserEntity userUp = userRepository.findByUserId(user.getUserId())
		                  .orElseThrow(()-> new ApiException(ApiStatus.NOT_EXIST_INFORMATION));
		try{
			//비밀번호 변경된 경우 암호화해서 SET
			if(!matchesPassword(user.getUserPassword(),userUp.getUserPassword())){
				String enPw = encodePassword(user.getUserPassword());
				user.setUserPassword(enPw);
			}
			//정보변경
			userRepository.save(user);
		} catch(Exception e){
			log.error("error updating user",user.getUserId(),e);
			new ApiException(ApiStatus.UNEXPECTED_ERROR);
		}
		return null;
	}

	//사용자정보삭제
	@Transactional
	public UserEntity deleteUser (final UserEntity user) {
		//해당 아이디 조회
		if(!userRepository.existsByUserId(user.getUserId())) {
			log.warn("No UserId", user.getUserId());
			throw new ApiException(ApiStatus.NOT_EXIST_INFORMATION);
		}
		try{
			userRepository.delete(user);
		} catch(Exception e){
			log.error("error deleting user",user.getUserId(),e);
			new ApiException(ApiStatus.UNEXPECTED_ERROR);
		}
		return null;
	}

	//사용자 패널티 부과
	@Transactional
	public UserEntity givePenaltyUser (final String userId){
		//패널티 부과시 추가 정보 입력받을수도 있을것 같아서 INPUT 객체로
		//해당 아이디 조회
		UserEntity user = userRepository.findByUserId(userId)
		                  .orElseThrow(()-> new ApiException(ApiStatus.NOT_EXIST_INFORMATION));
		try{
			// 패널티 횟수에 따라서 사용자 상태 변경
			if(user.getPenaltyCount() < 3){
				user.setPenaltyCount(user.getPenaltyCount()+1);
				user.setStatus(Status.ACTIVATED);
			}else{
				user.setStatus(Status.BANNED);
			}
			// 패널티 정보 업데이트
			userRepository.save(user);
		} catch(Exception e){
			log.error("error accusing user",user.getUserId(),e);
			new ApiException(ApiStatus.UNEXPECTED_ERROR);
		}
		return null;
	}

		//사용자 정상 로그인시 인증정보 GET
		@Transactional
		public Token getByCredentials(final String userId, final String userPassword) {
			UserEntity results = userRepository.findByUserId(userId)
			.orElseThrow(()-> new ApiException(ApiStatus.NOT_EXIST_INFORMATION));

			if(matchesPassword(userPassword,results.getUserPassword())){
				//토큰 생성
				Token issuedToken = tokenProvider.createToken(results);
				//String accessToken = issuedToken.getAccessToken();
			  // UserLoginDto user = new UserLoginDto(results);
				// UserLoginDto responseUserDTO = UserLoginDto.builder()
				// .userMailAddress(user.getUserMailAddress())
				// .userId(user.getUserId())
				// .token(accessToken)
				// .build();
				return issuedToken ; //responseUserDTO ;
			}else{
				return null;
			}
		}

	//사용자정보 전체조회 (ADMIN)
	@Transactional
	public List<UserDto> findAll() {
		//요청한 사용자가 관리자가 아닐경우 오류처리
		return userRepository.findAll().stream()
					.map(UserDto::new)
					.collect(Collectors.toList());
	}

	//관리자사용자로 변경 (ADMIN)
	@Transactional
	public UserEntity chageUserType (final String userId) {
		//해당 아이디 조회
		UserEntity user = userRepository.findByUserId(userId)
	                     	.orElseThrow(()-> new ApiException(ApiStatus.NOT_EXIST_INFORMATION));
		try{
			user.setUserType(UserType.ADMIN);
			userRepository.save(user);
		} catch(Exception e){
			log.error("error changing user type",user.getUserId(),e);
			new ApiException(ApiStatus.UNEXPECTED_ERROR);
		}
		return null;
	}

	//비밀번호 암호화
	private String encodePassword (final String pwdBf){
		if(pwdBf == ""){
			throw new ApiException(ApiStatus.REQUIRED_INFORMATION);
		}
		String pwdAf = passwordEncoder.encode(pwdBf);
		return pwdAf;
	}

	//비밀번호 암호화값 일치 여부 check
	private Boolean matchesPassword (final String pwdInput, final String pwdDb){
		if(passwordEncoder.matches(pwdInput, pwdDb)){
			return true;
		}else{
			return false;
		}
	}
}
