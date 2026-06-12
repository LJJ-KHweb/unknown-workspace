package com.kh.unknown.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class LoginResponse {
	private Long adminNo;
	private String adminId;
	private String adminName;
	private String role;
	private String accessToken;
	private String refreshToken;

}