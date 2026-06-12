package com.kh.unknown.auth.model.service;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.unknown.admin.model.dto.AdminDto;
import com.kh.unknown.auth.model.dao.AuthMapper;
import com.kh.unknown.auth.model.vo.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final AuthMapper authMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//log.info("과연?? > {}", username);
		// 여기선 우리가 무엇을 해야하는가?
		
		AdminDto user = authMapper.loadUser(username);
		 
		log.info("조회된 정보 : {}", user);
		
		if(user == null) {
			throw new UsernameNotFoundException("요거 있다구요잇");
		}
		
		
		
		return CustomUserDetails.builder().username(String.valueOf(user.getAdminNo()))
											.adminId(user.getAdminId())
											.password(user.getAdminPwd())
											.adminName(user.getAdminName())
											.authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())))
											.status(user.getStatus())
											.build();
	}

}