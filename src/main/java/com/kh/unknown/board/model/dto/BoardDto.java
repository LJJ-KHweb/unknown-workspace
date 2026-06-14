package com.kh.unknown.board.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
	private Long boardNo;
	@NotBlank(message="한 글자라도 들어와야함")
	@Size(max=30, message="너무 길어")
	private String boardTitle;
	@NotBlank(message="한 글자라도 들어와야함")
	private String boardContent;
	@NotNull
	private Integer boardQuestion;
	@NotBlank(message="한 글자라도 들어와야함")
	@Size(max=30, message="너무 길어")
	private String boardPwd;
	@Size(max=30, message="너무 길어")
	private String boardHint;
	@NotBlank
	private String boardCategory;
	private String fileUrl;
	private LocalDateTime createDate;
	private String modified;
	
}