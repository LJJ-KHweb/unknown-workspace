package com.kh.unknown.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.unknown.board.model.dto.BoardDto;
import com.kh.unknown.board.model.vo.Board;
import com.kh.unknown.page.model.dto.PageDto;

@Mapper
public interface BoardMapper {

	@Insert("INSERT INTO UNKNOWN_BOARD VALUES (#{boardNo}, #{boardTitle}, #{boardContent}, #{boardQuestion}, #{boardPwd}, #{boardHint}, #{fileUrl}, SYSDATE, 'N')")
	int save(Board board);
	
	@Insert("""
				INSERT
				  INTO
				  		CATEGORY
			    VALUES
						(
						#{boardNo}
					 ,	#{boardCategory}
					 	)
			""")
	int saveCategory(BoardDto board );
	
	@Select("""
				SELECT
					   BOARD_NO
			         , BOARD_TITLE
			         , CREATE_DATE
			         , FILE_URL
				 FROM
			 		  UNKNOWN_BOARD
			  	 JOIN
			  	 	  CATEGORY ON (CATEGORY.REF_BNO = UNKNOWN_BOARD.BOARD_NO)
			    WHERE 
			   		  NOT EXISTS (SELECT 1 FROM DELETE_BOARD WHERE BOARD_NO = REF_BNO)
			   	  AND
			   	  	  CATEGORY_NAME = #{boardCategory}
			    ORDER
			       BY
			       	  CREATE_DATE DESC
			    OFFSET #{page.offset} ROWS FETCH NEXT #{page.size} ROWS ONLY
			""")
	List<BoardDto> findAll(@Param("page")PageDto pageInfo, @Param("boardCategory") String boardCategory);
	
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
	
	@Update("""
			UPDATE 
					UNKNOWN_BOARD 
			   SET 
			   		BOARD_TITLE = #{boardTitle}
			   	 , 	BOARD_CONTENT = #{boardContent}
			   	 , 	FILE_URL = #{fileUrl}
			   	 , 	MODIFIED = 'Y' 
			 WHERE 
			 		BOARD_QUESTION = #{boardQuestion} 
			   AND 
			   		BOARD_PWD = #{boardPwd} 
			   AND 
			   		BOARD_NO = #{boardNo}
			""")
	int update(Board board);
	
	@Delete("INSERT INTO DELETE_BOARD VALUES(#{boardNo},SYSDATE)")
	int delete(Board board);

	@Select("SELECT SEQ_UBN.NEXTVAL FROM DUAL")
	Long selectBoardNo();
	
	@Update("""
				UPDATE
						CATEGORY
				   SET
				   		CATEGORY_NAME = #{boardCategory}
				 WHERE
				 		REF_BNO = #{boardNo}
			""")
	int updateCategory(@Param("boardNo") Long boardNo,@Param("boardCategory") String boardCategory);


	
}