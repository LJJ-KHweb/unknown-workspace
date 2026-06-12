package com.kh.unknown.board.model.vo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Board {
	private Long boardNo;
	private String boardTitle;
	private String boardContent;
	private Integer boardQuestion;
	private String boardPwd;
	private String boardHint;
	private String fileUrl;
	private LocalDateTime createDate;
	private String modified;
}