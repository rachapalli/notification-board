package com.board.notification.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.board.notification.service.impl.NotificationUserDetailsService;

@EnableWebSecurity
public class NotificationSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private NotificationUserDetailsService userDetailsService ;
	
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService);
	}
	
}