package com.kh.unknown.admin.model.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class Admin {
	private Long adminNo;
	private String adminId;
	private String adminPwd;
	private String adminName;
	private String role;
	private LocalDateTime createDate;
	private String status;
}