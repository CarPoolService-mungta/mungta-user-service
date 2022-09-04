package com.mungta.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;
import com.mungta.user.auth.JwtAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
		.csrf().disable()
		.formLogin().disable()
		.httpBasic().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests().antMatchers("/","/users/**","/swagger-ui/**", "/v3/api-docs/**").permitAll()
		.anyRequest().authenticated();

		http.addFilterAfter(jwtAuthenticationFilter,CorsFilter.class);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}


