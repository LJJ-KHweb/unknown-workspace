package com.kh.unknown.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.unknown.api.model.dto.ApiResponse;
import com.kh.unknown.api.model.dto.HttpState;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PostCreateException.class)
	public ResponseEntity<ApiResponse> PostCreate(PostCreateException e) {
		return ResponseEntity.status(HttpState.STATE_400.getCode()).body(ApiResponse.createFail(e.getMessage() , null));
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiResponse> notFound(NotFoundException e) {
		return ResponseEntity.status(HttpState.STATE_404.getCode()).body(ApiResponse.notFound("게시글 조회실패" , null));
	} 
	
	@ExceptionHandler(DeleteException.class)
	public ResponseEntity<ApiResponse> failDelete(DeleteException e) {
		return ResponseEntity.status(HttpState.STATE_400.getCode()).body(ApiResponse.notFound(e.getMessage() , null));
	} 
	
	@ExceptionHandler(DuplicateException.class)
	public ResponseEntity<ApiResponse> duplicate(DuplicateException e) {
		return ResponseEntity.status(HttpState.STATE_400.getCode()).body(ApiResponse.notFound(e.getMessage() , null));
	} 
	
}