package com.kh.unknown.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.unknown.admin.model.dto.AdminDto;
import com.kh.unknown.admin.model.service.AdminService;
import com.kh.unknown.api.model.dto.ApiResponse;
import com.kh.unknown.api.model.dto.HttpState;
import com.kh.unknown.board.model.dto.BoardDto;
import com.kh.unknown.page.model.dto.PageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminService adminService;
	
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> signup(@RequestBody AdminDto admin) {
		log.info("OOOOOOOOOOOOOOOOOOOOOOOOOOO");
		adminService.signup(admin);
		
		return ResponseEntity.status(HttpState.STATE_201.getCode()).body(ApiResponse.success("계정 생성 성공", null));
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse> logout(@RequestParam(value="adminNo") Long adminNo){
		//log.info("들어옴ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ");
		adminService.logout(adminNo);
		return ResponseEntity.status(HttpState.STATE_201.getCode()).body(ApiResponse.success("로그 아웃 성공", null));
	}
	@GetMapping("/deletedBoards/{boardNo}")
	public ResponseEntity<Long> findAllDeleteBoards(@PathVariable(name = "boardNo") Long boardNo){
		//log.info("들어옴ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ");
		log.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		Long boardNums = adminService.findByDeleteBoardNo(boardNo);
		return ResponseEntity.status(HttpState.STATE_201.getCode()).body(boardNums);
	}
	
	@GetMapping("/boards")
	public ResponseEntity<List<BoardDto>> findAllBoards(@RequestParam(name="page", defaultValue = "0") int page, @RequestParam(name="size") int size, @RequestParam(name="category") String category) {
		log.info("들어오나요?@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		List<BoardDto> boards = adminService.findAllBoards(new PageDto(page, size), category);
		return ResponseEntity.status(200).body(boards);
	}
	@GetMapping("/{boardNo}")
	public ResponseEntity<BoardDto> findByBoardNo(@PathVariable(name = "boardNo") Long boardNo) {
		BoardDto board = adminService.findByBoardNo(boardNo);
		return ResponseEntity.status(200).body(board);
	}
	@DeleteMapping("/{boardNo}")
	public ResponseEntity<ApiResponse> deleteBoardDelete(@PathVariable(name = "boardNo") Long boardNo) {
		adminService.deleteBoardDelete(boardNo);
		return ResponseEntity.status(HttpState.STATE_201.getCode()).body(ApiResponse.success("삭제 테이블 삭제 성공", null));
	}
	
}