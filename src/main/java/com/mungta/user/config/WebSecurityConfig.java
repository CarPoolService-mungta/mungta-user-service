package com.mungta.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import com.mungta.user.auth.JwtAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {// filterChain(HttpSecurity http) throws Exception

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// http 시큐리티 빌더
		http.cors().and()          // WebMvcConfig에서 이미 설정했으므로 기본 cors 설정.
				.csrf().disable()      // csrf는 현재 사용하지 않으므로 disable
				.formLogin().disable() // 기본로그인 페이지 없애기
				.httpBasic().disable() // token을 사용하므로 basic 인증 disable
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)   // session 기반이 아님을 선언
				.and()
				.authorizeRequests().antMatchers("/", "/users/**").permitAll() //"/users/auth/**")-/와 /auth/** 경로는 인증 안해도 됨.
				.anyRequest().authenticated(); ///와 /auth/**이외의 모든 경로는 인증 해야됨.
				// .and()
				// .formLogin()
				// .loginPage("/user/loginView") // 로그인 페이지를 제공하는 URL을 설정함
				// .successForwardUrl("/index")  // 로그인 성공 URL을 설정함
				// .failureForwardUrl("/index")  // 로그인 실패 URL을 설정함
				// .permitAll()
				// .and()
				// .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		// filter 등록.
		// 매 리퀘스트마다
		// CorsFilter 실행한 후에
		// jwtAuthenticationFilter 실행한다.
		http.addFilterAfter(jwtAuthenticationFilter,
					            	CorsFilter.class
											  );
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}


