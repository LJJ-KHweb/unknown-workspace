package com.kh.unknown.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.unknown.board.model.dto.BoardDto;
import com.kh.unknown.board.model.vo.Board;

@Mapper
public interface BoardMapper {

	@Insert("INSERT INTO UNKNOWN_BOARD VALUES (SEQ_UBN.NEXTVAL, #{boardTitle}, #{boardContent}, #{boardQuestion}, #{boardPwd}, #{boardHint}, #{fileUrl}, SYSDATE, 'N')")
	int save(Board board);
	
	@Select("""
				SELECT
					   BOARD_NO
			         , BOARD_TITLE
			         , CREATE_DATE
				 FROM
			 		  UNKNOWN_BOARD
			    WHERE
			   		  NOT EXISTS (SELECT 1 FROM DELETE_BOARD WHERE BOARD_NO = REF_BNO)
			""")
	List<BoardDto> findAll();
	
	@Select("""
				SELECT
						BOARD_NO
				   	 ,	BOARD_TITLE
				   	 ,	BOARD_CONTENT
				   	 ,	BOARD_QUESTION
				   	 ,	BOARD_PWD
				   	 ,	BOARD_HINT
				   	 ,	FILE_URL
				  FROM
				  		UNKNOWN_BOARD
				 WHERE
				 		BOARD_NO = #{boardNo}
				   AND
				   		NOT EXISTS (SELECT 1 FROM DELETE_BOARD WHERE BOARD_NO = REF_BNO)
			""")
	BoardDto findByBoardNo(Long boardNo);
	
	@Update("UPDATE UNKNOWN_BOARD SET BOARD_TITLE = #{boardTitle}, BOARD_CONTENT = #{boardContent}, MODIFIED = 'Y' WHERE BOARD_QUESTION = #{boardQuestion} AND BOARD_PWD = #{boardPwd} AND BOARD_NO = #{boardNo}")
	int update(Board board);
	
	@Delete("INSERT INTO DELETE_BOARD VALUES(#{boardNo},SYSDATE)")
	int delete(Long boardNo);
	
}