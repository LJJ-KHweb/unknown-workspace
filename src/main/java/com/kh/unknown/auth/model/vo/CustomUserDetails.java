package com.kh.unknown.auth.model.vo;

import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomUserDetails implements UserDetails {
	private String username; // ADMIN_NO 담겠음
	private String adminId;
	private String password;
	private String adminName;
	private Collection<? extends GrantedAuthority> authorities;
	private String status;
	

}