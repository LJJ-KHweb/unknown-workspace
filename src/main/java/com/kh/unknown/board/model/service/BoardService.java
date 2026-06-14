package com.kh.unknown.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.unknown.board.model.dao.BoardMapper;
import com.kh.unknown.board.model.dto.BoardDto;
import com.kh.unknown.board.model.vo.Board;
import com.kh.unknown.exception.DeleteException;
import com.kh.unknown.exception.NotFoundException;
import com.kh.unknown.exception.PostCreateException;
import com.kh.unknown.file.FileService;
import com.kh.unknown.page.model.dto.PageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

	private final BoardMapper boardMapper;
	private final FileService fileService;
	
	@Transactional
	public void save(BoardDto board, MultipartFile file) {
		
		board.setBoardNo(boardMapper.selectBoardNo());
		
		Board boardEntity = Board.builder()
				.boardNo(board.getBoardNo())
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
		
		result = boardMapper.saveCategory(board);
		if(result < 1) {
			throw new PostCreateException("카테고리 작성 실패");
		}
	}
	@Transactional
	public List<BoardDto> findAll(PageDto pageInfo, String category){
		return boardMapper.findAll(pageInfo, category);
	}
	@Transactional
	public void update(BoardDto board, Long boardNo, MultipartFile file) {
		
		if(findByBoardNo(boardNo) == null) {
			throw new NotFoundException("없는 게시글 조회");
		} 
		log.info("{}33333333333333333333333333333333333333333333333",board);
		
		Board boardEntity = Board.builder()
								.boardNo(boardNo)
								.boardTitle(board.getBoardTitle())
								.boardContent(board.getBoardContent())
								.boardQuestion(board.getBoardQuestion())
								.boardPwd(board.getBoardPwd())
								.fileUrl((file != null && !file.isEmpty()) ? fileService.store(file): null)
								.build();
		log.info("ServiceBoard : {}", boardEntity);
		int result = boardMapper.update(boardEntity);
		
		if(result < 1) {
			throw new PostCreateException("게시글 수정 실패");
		}
		
		result = boardMapper.updateCategory(board.getBoardNo(), board.getBoardCategory());
		if(result < 1) {
			throw new PostCreateException("게시글 수정 실패");
		}
	}
	
	@Transactional
	public void delete(Long boardNo, BoardDto board) {
		log.info("delete들어모");
		BoardDto responseBoard = boardMapper.findByBoardNo(boardNo);
		if(responseBoard == null) {
			throw new NotFoundException("없는 게시글");
		}
		
		if(responseBoard.getBoardQuestion() != board.getBoardQuestion() || !responseBoard.getBoardPwd().equals(board.getBoardPwd())) {
			throw new NotFoundException("대답이 맞지 않습니다.");
		}
		
		Board boardEntity = Board.builder()
											.boardNo(boardNo)
											.boardQuestion(board.getBoardQuestion())
											.boardPwd(board.getBoardPwd())
											.build();
		
		int result = boardMapper.delete(boardEntity);
		
		
		if(result < 1) {
			throw new DeleteException("삭제 실패");
		}
	}
	@Transactional
	public BoardDto findByBoardNo(Long boardNo) {
		
		
		if(boardMapper.findByBoardNo(boardNo) == null) {
			throw new NotFoundException("없는 게시글 조회");
		}
		
		
		
		return boardMapper.findByBoardNo(boardNo);
	}
	
}