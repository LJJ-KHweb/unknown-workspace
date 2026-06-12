package com.kh.unknown.admin.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.unknown.admin.model.dao.AdminMapper;
import com.kh.unknown.admin.model.dto.AdminDto;
import com.kh.unknown.admin.model.vo.Admin;
import com.kh.unknown.exception.DuplicateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminMapper adminMapper;
	private final PasswordEncoder pwdEncoder;
	
	public void signup(AdminDto admin) {
		
		int count = adminMapper.findAdminIdCount(admin.getAdminId());
		
		if(count > 0) {
			throw new DuplicateException("아이디 중복");
		}
		
		Admin adminEntity = Admin.builder().adminId(admin.getAdminId())
											.adminPwd(pwdEncoder.encode(admin.getAdminPwd()))
											.adminName(admin.getAdminName()).build();
		
		adminMapper.signup(adminEntity);
		
	}
	
}