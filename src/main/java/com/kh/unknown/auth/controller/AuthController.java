package com.kh.unknown.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.unknown.auth.model.dto.LoginRequestDto;
import com.kh.unknown.auth.model.dto.LoginResponse;
import com.kh.unknown.auth.model.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/auth")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto lrd){
		log.info("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO{}",lrd);
		LoginResponse res = authService.login(lrd);	
		return ResponseEntity.ok(res);
	}
}