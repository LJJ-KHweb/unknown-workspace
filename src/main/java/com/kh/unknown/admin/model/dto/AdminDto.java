package com.kh.unknown.admin.model.dto;

import java.time.LocalDateTime;

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
public class AdminDto {
	private Long adminNo;
	private String adminId;
	private String adminPwd;
	private String adminName;
	private String role;
	private LocalDateTime createDate;
	private String status;
}