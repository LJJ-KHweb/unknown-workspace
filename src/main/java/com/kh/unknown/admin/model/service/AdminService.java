package com.kh.unknown.admin.model.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.unknown.admin.model.dao.AdminMapper;
import com.kh.unknown.admin.model.dto.AdminDto;
import com.kh.unknown.admin.model.vo.Admin;
import com.kh.unknown.board.model.dto.BoardDto;
import com.kh.unknown.exception.DuplicateException;
import com.kh.unknown.exception.LogoutFailException;
import com.kh.unknown.exception.NotFoundException;
import com.kh.unknown.page.model.dto.PageDto;
import com.kh.unknown.token.model.dao.TokenMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminMapper adminMapper;
	private final PasswordEncoder pwdEncoder;
	private final TokenMapper tokenMapper;
	
	public void signup(AdminDto admin) {
		
		int count = adminMapper.findAdminIdCount(admin.getAdminId());
		
		if(count > 0) {
			throw new DuplicateException("아이디 중복");
		}
		
		Admin adminEntity = Admin.builder().adminId(admin.getAdminId())
											.adminPwd(pwdEncoder.encode(admin.getAdminPwd()))
											.adminName(admin.getAdminName()).build();
		
		adminMapper.signup(adminEntity);
		
	}

	@Transactional
	public void logout(Long adminNo) {
		//log.info("admin 로그아웃 전 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		if(adminMapper.findAdminNoCount(adminNo) <1) {
			throw new LogoutFailException("로그아웃 실패");
		}
		//log.info("admin성공 토큰 삭제 전 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		if(tokenMapper.deleteToken(adminNo)<1) {
			throw new LogoutFailException("토큰 삭제 실패");
		}
		//log.info("토큰 삭제후 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}

	public List<BoardDto> findAllBoards(PageDto pageDto, String category) {
		
		List<BoardDto> boards = adminMapper.findAllBoards(pageDto, category);
		if(boards == null) {
			throw new NotFoundException("조회된 게시글이 없음");
		}
		return boards;
	}

	public BoardDto findByBoardNo(Long boardNo) {
		BoardDto board = adminMapper.findByBoardNo(boardNo);
		if(board == null) {
			throw new NotFoundException("해당 게시글이 없음");
		}
		return board;
	}

	public void deleteBoardDelete(Long boardNo) {
		adminMapper.deleteBoardDelete(boardNo);
	}

	public Long findByDeleteBoardNo(Long boardNo) {
		Long boardNums =adminMapper.findByDeleteBoardNo();
		return boardNums;
	}
	
}