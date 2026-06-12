package com.kh.unknown.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.unknown.api.model.dto.ApiResponse;
import com.kh.unknown.board.model.dao.BoardMapper;
import com.kh.unknown.board.model.dto.BoardDto;
import com.kh.unknown.board.model.vo.Board;
import com.kh.unknown.exception.DeleteException;
import com.kh.unknown.exception.NotFoundException;
import com.kh.unknown.exception.PostCreateException;
import com.kh.unknown.file.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

	private final BoardMapper boardMapper;
	private final FileService fileService;
	
	public void save(BoardDto board, MultipartFile file) {
		
		Board boardEntity = Board.builder()
				.boardTitle(board.getBoardTitle())
				.boardContent(board.getBoardContent())
				.boardQuestion(board.getBoardQuestion())
				.boardPwd(board.getBoardPwd())
				.boardHint(board.getBoardHint())
				.fileUrl((file != null && !file.isEmpty()) ? fileService.store(file): null)
				.build();
		
		int result = boardMapper.save(boardEntity);
		
		if(result < 1) {
			throw new PostCreateException("게시글 작성 실패");
		}
		
	}
	
	public List<BoardDto> findAll(){
		return boardMapper.findAll();
	}
	
	public void update(BoardDto board, Long boardNo) {
		
		if(findByBoardNo(boardNo) == null) {
			throw new NotFoundException("없는 게시글 조회");
		}
		
		Board boardEntity = Board.builder()
								.boardNo(boardNo)
								.boardTitle(board.getBoardTitle())
								.boardContent(board.getBoardContent())
								.boardQuestion(board.getBoardQuestion())
								.boardPwd(board.getBoardPwd())
								.build();
		log.info("ServiceBoard : {}", boardEntity);
		int result = boardMapper.update(boardEntity);
		
		if(result < 1) {
			throw new PostCreateException("게시글 수정 실패");
		}
	}
	
	
	public void delete(Long boardNo) {
		if(findByBoardNo(boardNo) == null) {
			throw new NotFoundException("없는 게시글");
		}
		int result = boardMapper.delete(boardNo);
		
		if(result < 0) {
			throw new DeleteException("삭제 실패");
		}
	}

	public BoardDto findByBoardNo(Long boardNo) {
		if(boardMapper.findByBoardNo(boardNo) == null) {
			throw new NotFoundException("없는 게시글 조회");
		}
		
		return boardMapper.findByBoardNo(boardNo);
	}
	
}