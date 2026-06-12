package com.kh.unknown.api.model.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum HttpState {
	STATE_200(HttpStatus.OK),
	STATE_201(HttpStatus.CREATED),
	STATE_202(HttpStatus.ACCEPTED),
	STATE_204(HttpStatus.NO_CONTENT),
	STATE_400(HttpStatus.BAD_REQUEST),
	STATE_401(HttpStatus.UNAUTHORIZED),
	STATE_403(HttpStatus.FORBIDDEN),
	STATE_404(HttpStatus.NOT_FOUND),
	STATE_405(HttpStatus.METHOD_NOT_ALLOWED),
	STATE_500(HttpStatus.INTERNAL_SERVER_ERROR);
	
	private final HttpStatus code;
	
	HttpState(HttpStatus code){
		this.code = code;
	}
}