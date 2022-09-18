package com.mungta.user.api;

import com.mungta.user.dto.*;
import com.mungta.user.model.UserEntity;
import com.mungta.user.service.UserService;
import com.mungta.user.service.AuthenticationService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


@Slf4j
@Tag(name="사용자관리API", description = "사용자관리서비스")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private final UserService userService;

  @Autowired
  private final AuthenticationService authenticationService;

  @Operation(summary = "사용자 상세 정보 조회", description  = "사용자ID를 통해 사용자 정보를 조회한다.")
  @Parameter(name = "userId", description  = "사용자ID",in = ParameterIn.PATH)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "User information has been searched",content = @Content(schema = @Schema(implementation = UserDto.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error")})
  @GetMapping("/{userId}")
  @ResponseBody
  public ResponseEntity<?>getUser(@PathVariable String userId) {
    UserDto response = userService.getUser(userId);
    return  ResponseEntity.ok(response);
  }

  @Operation(summary = "사용자 사진 조회", description = "사용자 사진을 조회한다.")
  @GetMapping(value="/auth/downloadFile/{userId}")
  private  ResponseEntity<Map<String, Object>> preView (@PathVariable String userId)  {
    UserResponseDto userResponseDto = userService.getUserPhoto(userId);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("fileExtension", userResponseDto.getFileExtension());
    params.put("userPhoto"    , (new String(Base64.encodeBase64(userResponseDto.getUserPhoto()))));
    return ResponseEntity.ok(params);
  }

  @Operation(summary = "사용자 등록", description = "사용자를 등록한다.(Sign up)")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "User information has been created"),
    @ApiResponse(responseCode = "500", description = "Internal server error")})
  @PostMapping("/auth/signup")
  public  ResponseEntity<?> registerUser(@RequestBody final UserDto userDto){
    log.debug("################ UserController INPUT : "+ToStringBuilder.reflectionToString(userDto));
    UserEntity user = UserDto.toEntity(userDto);
    userService.createUser(user);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "사용자 등록한다.(with pic)", description = "사용자를 등록한다.(Sign up)")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "User information has been created"),
    @ApiResponse(responseCode = "500", description = "Internal server error")})
  @PostMapping("/auth/signup/test")
  public  ResponseEntity<String> registerUserWithPhoto(@ModelAttribute UserRequestDto userRequestDto){
    String fileName ="";
    UserEntity user = UserRequestDto.toEntity(userRequestDto);
    fileName =  userService.createUserWithPhoto(user,userRequestDto.getProfileImg());
    return ResponseEntity.ok(fileName);
  }

  @Operation(summary = "사용자 수정", description = "사용자 정보를 수정한다.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "User information has been updated"),
    @ApiResponse(responseCode = "404", description = "Not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")})
  @PutMapping(value="/{userId}")
  public  ResponseEntity<?> updateUser(@RequestBody final UserDto userDto){
    UserEntity user = UserDto.toEntity(userDto);
    userService.updateUser(user);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "사용자 삭제", description = "사용자 정보를 삭제한다.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "User Deleted OK"),
    @ApiResponse(responseCode = "404", description = "Not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")})
  @DeleteMapping(value="/{userId}")
  public  ResponseEntity<?> deleteUser(@RequestBody final UserDto userDto){
      UserEntity user = UserDto.toEntity(userDto);
      userService.deleteUser(user);
      return ResponseEntity.ok().build(); //ResponseEntity.noContent().build();
  }

  @Operation(summary = "사용자 패널티 부여", description = "사용자 패널티를 부여한다.")
  @Parameter(name = "userId", description  = "사용자ID",in = ParameterIn.PATH)
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Penalty grant is complete."),
    @ApiResponse(responseCode = "404", description = "Not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")})
  @PutMapping(value="/penalty/{userId}")
  public  ResponseEntity<?> givePenaltyUser(@PathVariable String userId){
    userService.givePenaltyUser(userId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "로그인", description = "로그인 한다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode  = "200", description  = "login OK"),
    @ApiResponse(responseCode  = "400", description  = "Bad Request"),
    @ApiResponse(responseCode  = "500", description  = "Internal server error")})
  @PostMapping("/auth/signin")
  public ResponseEntity<?> authenticate(@RequestBody UserLoginDto userLoginDto) {
    log.debug("################ UserController authenticate : "+ToStringBuilder.reflectionToString(userLoginDto));
    Token issuedToken = userService.getByCredentials(userLoginDto.getUserId(),
                                                     userLoginDto.getUserPassword());
//    if(issuedToken != null) {
//      return ResponseEntity.ok().body(issuedToken);
//    } else {
//      return ResponseEntity.badRequest()
//                           .body(ResponseDto.builder().errorCode(ApiStatus.LOGIN_FAILED.getCode())
//                                                      .message(ApiStatus.LOGIN_FAILED.getMessage())
//                                                      .build());
//    }
    return ResponseEntity.ok().body(issuedToken);
  }
  //로그인
  @Operation(summary = "리프레시", description = "토큰 리프레시")
  @ApiResponses(value = {
          @ApiResponse(responseCode  = "200", description  = "login OK"),
          @ApiResponse(responseCode  = "400", description  = "Bad Request"),
          @ApiResponse(responseCode  = "500", description  = "Internal server error")})
  @PutMapping("/token-refresh")
  public ResponseEntity<String> refreshAccessToken(@RequestHeader("userId") String userId) {
    return ResponseEntity.ok().body(userService.tokenRefresh(userId));
  }

  @Operation(summary = "메일 인증 발송", description = "메일 인증을 진행한다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode  = "200", description  = "send email OK"),
    @ApiResponse(responseCode  = "404", description  = "Not found"),
    @ApiResponse(responseCode  = "500", description  = "Internal server error")})
  @PostMapping("/auth/mail")
  public ResponseEntity<?> sendEmailAuthNumber(@RequestBody @Valid AuthenticationDto authDto) {
    authenticationService.sendAuthNumber(authDto.getUserMailAddress());
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "메일 인증 확인", description = "메일 인증번호를 확인한다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode  = "200", description  = "verification number check OK"),
    @ApiResponse(responseCode  = "404", description  = "Not found"),
    @ApiResponse(responseCode  = "500", description  = "Internal server error")})
  @GetMapping(value="/auth/confirm")
  public ResponseEntity<?> checkAuthNumber (AuthenticationDto authDto) {

    AuthenticationDto response = authenticationService.checkAuthNumber(AuthenticationDto.toEntity(authDto));
    return  ResponseEntity.ok(response);
  }

  @Operation(summary = "사용자를 관리자로 변경", description = "사용자유형을 관리자로 수정한다.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Becoming an administrator is complete."),
    @ApiResponse(responseCode = "404", description = "Not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")})
  @PutMapping(value="/admin/{userId}")
  public ResponseEntity<?>updateAdminRole(@PathVariable String userId){
    userService.chageUserType(userId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "사용자 정보 전체 조회", description  = "전체 사용자 정보를 조회한다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode  = "200", description  = "User information has been searched"),
    @ApiResponse(responseCode  = "404", description  = "Not found"),
    @ApiResponse(responseCode  = "500", description  = "Internal server error")})
  @GetMapping("/admin")
  public ResponseEntity<?>getUserListAdmin(UserDto userDto) {
    List<UserDto> response =  userService.findAll();
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "사용자전체조회(사용자선택가능)", description = "사용자전체조회")
  @GetMapping
  @ResponseBody
  public ResponseEntity<List<UserDto>> getUserList(@RequestParam List<String> userIds){
    List<UserDto> response = new ArrayList<>();
    for (String userId : userIds) {
      response.add(userService.getUser(userId));
    }
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "운전자 정보 조회(사용자선택가능)", description = "운전자 정보 조회")
  @GetMapping("/driver-info")
  @ResponseBody
  public ResponseEntity<DriverInfoRequest> getDriverInfo(@RequestHeader("userId") String userId){
    return ResponseEntity.ok(userService.getDriverInfo(userId));
  }


}


