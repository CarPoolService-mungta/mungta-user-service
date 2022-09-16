package com.mungta.user.api;

import com.mungta.user.dto.UserDto;
import com.mungta.user.dto.UserLoginDto;
import com.mungta.user.model.UserEntity;
import com.mungta.user.dto.AuthenticationDto;
import com.mungta.user.dto.ResponseDto;
import com.mungta.user.dto.Token;
import com.mungta.user.service.UserService;
import com.mungta.user.service.AuthenticationService;
import com.mungta.user.service.StorageService;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
  private final StorageService storageService;

  @Autowired
  private final AuthenticationService authenticationService;

  //단건 조회
  @Operation(summary = "사용자 정보 조회", description  = "사용자ID를 통해 사용자 정보를 조회한다.")
  @Parameter(name = "userId", description  = "사용자ID",in = ParameterIn.PATH)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "User information has been searched",content = @Content(schema = @Schema(implementation = UserDto.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error")})
  @GetMapping("/{userId}")
  @ResponseBody
  public ResponseEntity<?>getUser(@PathVariable String userId) {
    UserDto response = userService.getUser(userId);
    //Resource file = storageService.loadAsResource(response.get);
    return  ResponseEntity.ok(response);/* .header(HttpHeaders.CONTENT_DISPOSITION,
    "attachment; filename=\"" + file.getFilename() + "\"").body(file);*/
  }

  //등록
  @Operation(summary = "사용자 등록", description = "사용자를 등록한다. (Sign up)")
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
  // 사진 업로드
  @Operation(summary = "사용자 사진 업로드", description = "사용자 사진을 업로드한다.")
  @PostMapping(value="/auth/uploadFile/{userId}")
  public ResponseEntity<String> uploadFile(@PathVariable String userId, @RequestBody final MultipartFile profileImg){
    String fileName ="";
    try {
      //File copyFile = new File("temptestId.jpg");
      //profileImg.transferTo(copyFile);
      fileName = storageService.store(userId,profileImg);
    } catch (Exception e) {
      throw new RuntimeException("Error");
    }
    return ResponseEntity.ok(fileName);
  }

  //수정
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

  //삭제
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

  //패널티 부여
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

  //로그인
  @Operation(summary = "로그인", description = "로그인 한다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode  = "200", description  = "login OK"),
    @ApiResponse(responseCode  = "400", description  = "Bad Request"),
    @ApiResponse(responseCode  = "500", description  = "Internal server error")})
  @PostMapping("/auth/signin")
  public ResponseEntity<?> authenticate(@RequestBody UserLoginDto userLoginDto) {
    Token issuedToken = userService.getByCredentials(userLoginDto.getUserId(),
                                                     userLoginDto.getUserPassword());
    if(issuedToken != null) {
      return ResponseEntity.ok().body(issuedToken);
    } else {
      return ResponseEntity.badRequest()
                           .body(ResponseDto.builder().error("Login failed.").build());
    }
  }

  //이메일 발송
  @Operation(summary = "메일 인증 발송", description = "메일 인증을 진행한다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode  = "200", description  = "send email OK"),
    @ApiResponse(responseCode  = "404", description  = "Not found"),
    @ApiResponse(responseCode  = "500", description  = "Internal server error")})
  @PostMapping("/auth/mail")
  public ResponseEntity<?> sendEmailAuthNumber(@RequestBody AuthenticationDto authDto) {
    authenticationService.sendAuthNumber(authDto.getUserMailAddress());
    log.debug("################ email send result : "+ToStringBuilder.reflectionToString(ResponseEntity.ok().build()));
    return ResponseEntity.ok().build();
  }

  //이메일 인증 확인
  @Operation(summary = "메일 인증 확인", description = "메일 인증번호를 확인한다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode  = "200", description  = "verification number check OK"),
    @ApiResponse(responseCode  = "404", description  = "Not found"),
    @ApiResponse(responseCode  = "500", description  = "Internal server error")})
  @GetMapping(value="/auth/confirm")
  public ResponseEntity<?> checkAuthNumber (AuthenticationDto authDto) {
    AuthenticationDto response = authenticationService.checkAuthNumber(AuthenticationDto.toEntity(authDto));
    log.debug("################ email check result : "+ToStringBuilder.reflectionToString(ResponseEntity.ok(response)));
    return  ResponseEntity.ok(response);
  }
  //전체 조회 (관리자 용)
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

  //관리자로 사용자 유형 변경  (관리자 용)
  @Operation(summary = "관리자로 변경", description = "사용자유형을 관리자로 수정한다.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Becoming an administrator is complete."),
    @ApiResponse(responseCode = "404", description = "Not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")})
  @PutMapping(value="/admin/{userId}")
  public ResponseEntity<?>updateAdminRole(@PathVariable String userId){
    userService.chageUserType(userId);
    return ResponseEntity.noContent().build();
  }
  @Operation(summary = "사용자전체조회", description = "사용자전체조회")
  @GetMapping
  @ResponseBody
  public ResponseEntity<List<UserDto>> getUserList(@RequestParam List<String> userIds){
    List<UserDto> response = new ArrayList<>();
    for (String userId : userIds) {
      response.add(userService.getUser(userId));
    }
    return ResponseEntity.ok(response);
  }
}


