package com.kh.unknown.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.unknown.admin.model.dto.AdminDto;
import com.kh.unknown.admin.model.service.AdminService;
import com.kh.unknown.api.model.dto.ApiResponse;
import com.kh.unknown.api.model.dto.HttpState;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminService adminService;
	
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> signup(@RequestBody AdminDto admin) {
		adminService.signup(admin);
		
		return ResponseEntity.status(HttpState.STATE_201.getCode()).body(ApiResponse.success("계정 생성 성공", null));
	}
	
}