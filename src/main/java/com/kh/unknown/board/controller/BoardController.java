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
	public ResponseEntity<List<BoardDto>> findAll() {
		List<BoardDto> boards = boardService.findAll();

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
		boardService.save(board, file);
		return ResponseEntity.status(HttpState.STATE_201.getCode()).body(ApiResponse.success("게시글 작성성공", null));
	}

	@PatchMapping("/{boardNo}")
	public ResponseEntity<ApiResponse<Void>> update(@RequestBody BoardDto board, @PathVariable(name = "boardNo") Long boardNo) {
		log.info("board : {}", board);
		log.info("boardNo : {}", boardNo);

		boardService.update(board, boardNo);
		return ResponseEntity.status(HttpState.STATE_201.getCode()).body(ApiResponse.success("게시글 수정 성공", null));
	}

	@DeleteMapping("/{boardNo}")
	public ResponseEntity<?> delete(@PathVariable(name = "boardNo") Long boardNo) {
		boardService.delete(boardNo);
		return ResponseEntity.status(204).build();
	}
	
	
	
	
	
}