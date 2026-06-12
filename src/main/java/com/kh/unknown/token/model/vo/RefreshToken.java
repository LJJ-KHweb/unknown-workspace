package com.kh.unknown.token.model.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RefreshToken {
	private Long adminNo;
	private String token;
	private Long expiration;
	
}