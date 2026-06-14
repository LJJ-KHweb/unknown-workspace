package com.kh.unknown.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApiResponse<T> {
	private HttpState code;
	private String msg;
	private T data;
	
	public static <T> ApiResponse<T> createFail(String msg, T data) {
		return new ApiResponse<>(HttpState.STATE_400, msg, data); 
	}

	public static <T> ApiResponse<T> success(String msg, T data) {
		return new ApiResponse<>(HttpState.STATE_201, msg, data);
	}
	
	public static <T> ApiResponse<T> delete(String msg, T data) {
		return new ApiResponse<>(HttpState.STATE_204, msg, data);
	}
	
	public static <T> ApiResponse<T> notFound(String msg, T data) {
		return new ApiResponse<>(HttpState.STATE_404, msg, data);
	}
	
	public static <T> ApiResponse<T> failDelete(String msg, T data) {
		return new ApiResponse<>(HttpState.STATE_400, msg, data);
	}
	
}