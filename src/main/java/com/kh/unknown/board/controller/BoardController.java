package com.kh.unknown.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.unknown.api.model.dto.ApiResponse;
import com.kh.unknown.api.model.dto.HttpState;
import com.kh.unknown.board.model.dto.BoardDto;
import com.kh.unknown.board.model.service.BoardService;
import com.kh.unknown.page.model.dto.PageDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("/localhost:5173")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/boards")
public class BoardController {
	private final BoardService boardService;

	@GetMapping
	public ResponseEntity<List<BoardDto>> findAll(@RequestParam(name="page", defaultValue = "0") int page, @RequestParam(name="size") int size, @RequestParam(name="category") String category) {
		
		List<BoardDto> boards = boardService.findAll(new PageDto(page, size), category);
		
		return ResponseEntity.status(200).body(boards);
	}

	@GetMapping("/{boardNo}")
	public ResponseEntity<BoardDto> findByBoardNo(@PathVariable(name = "boardNo") Long boardNo) {
		BoardDto board = boardService.findByBoardNo(boardNo);

		return ResponseEntity.status(200).body(board);
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> save(@ModelAttribute @Valid BoardDto board, 
			@RequestParam(name = "file", required = false) MultipartFile file) {
		log.info("===============================================22222");
		//log.info("파일명: [{}]", file.getOriginalFilename());
		boardService.save(board, file);
		return ResponseEntity.status(HttpState.STATE_201.getCode()).body(ApiResponse.success("게시글 작성성공", null));
	}

	@PatchMapping("/{boardNo}")
	public ResponseEntity<ApiResponse<Void>> update(@ModelAttribute @Valid BoardDto board, @PathVariable(name = "boardNo") Long boardNo, @RequestParam(name = "file", required = false) MultipartFile file) {
		log.info("board : {}", board);
		log.info("boardNo : {}", boardNo);

		boardService.update(board, boardNo, file);
		//log.info("2222222222222222222222222222222222222222222{}", board);
		return ResponseEntity.status(HttpState.STATE_201.getCode()).body(ApiResponse.success("게시글 수정 성공", null));
	}

	@DeleteMapping("/{boardNo}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable(name = "boardNo") Long boardNo, @RequestBody BoardDto board) {
		log.info("333333333333333333333333333333333333333333{}", board);
		boardService.delete(boardNo, board);
		return ResponseEntity.status(HttpState.STATE_201.getCode()).body(ApiResponse.delete("삭제성공", null));
	}
	
	
	
	
	
}