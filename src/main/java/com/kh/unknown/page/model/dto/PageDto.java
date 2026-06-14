package com.kh.unknown.page.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageDto {
	private int page;
	private int size;
	
	public int getOffset() {
		return (page -1) *size;
	}
	
	
}
