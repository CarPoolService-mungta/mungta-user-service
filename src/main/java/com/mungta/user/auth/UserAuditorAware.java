package com.mungta.user.auth;

import com.mungta.user.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@Slf4j
public class UserAuditorAware  implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (null == authentication || !authentication.isAuthenticated()) {
          return null;
      }
      /*
      keyHash=-807328020,
      principal=anonymousUser,
      authenticated=true,
      authorities=[ROLE_ANONYMOUS],
      details=WebAuthenticationDetails
      */
      log.debug("################ authentication INPUT : "+ToStringBuilder.reflectionToString(authentication));
      log.debug("################ principal : "+ToStringBuilder.reflectionToString(authentication.getPrincipal()));
      // log.debug("ID",String.valueOf(authentication.getPrincipal()));
      // log.debug("ID111",authentication.getPrincipal());

      UserEntity user = new UserEntity();
      //if(authentication.getPrincipal() != null){
      // user = (UserEntity) authentication.getPrincipal();
      //}else{
      user.setUserId("anonymousUser");
      //}
      log.debug("getUserId",user.getUserId());
      return Optional.of(user.getUserId());
  }
}
