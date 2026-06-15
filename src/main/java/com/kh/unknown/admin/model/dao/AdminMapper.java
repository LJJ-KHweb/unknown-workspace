package com.kh.unknown.admin.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kh.unknown.admin.model.vo.Admin;
import com.kh.unknown.auth.model.dto.LoginRequestDto;
import com.kh.unknown.board.model.dto.BoardDto;
import com.kh.unknown.page.model.dto.PageDto;

@Mapper
public interface AdminMapper {
	
	@Select("SELECT ADMIN_NO FROM ADMIN WHERE ADMIN_ID = #{adminId} AND STATUS ='Y'")
	public Long findByAdminNo(LoginRequestDto lrd);
	
	@Insert("INSERT INTO ADMIN VALUES(SEQ_AN.NEXTVAL, #{adminId}, #{adminPwd}, #{adminName}, 'ROLE_ADMIN', SYSDATE, 'Y')")
	int signup(Admin admin);
	
	@Select("SELECT COUNT(*) FROM ADMIN WHERE ADMIN_ID = #{adminId}")
	int findAdminIdCount(String adminId);

	
	@Select("SELECT COUNT(*) FROM ADMIN WHERE ADMIN_NO = #{adminNo}")
	public int findAdminNoCount(Long adminNo);
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
		   	  	  CATEGORY_NAME = #{boardCategory}
		    ORDER
		       BY
		       	  CREATE_DATE DESC
		    OFFSET #{page.offset} ROWS FETCH NEXT #{page.size} ROWS ONLY
		""")
	public List<BoardDto> findAllBoards(@Param("page")PageDto pageInfo, @Param("boardCategory") String boardCategory);
	
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
		""")
	BoardDto findByBoardNo(Long boardNo);


	@Delete("""
				DELETE
				  FROM
				  		DELETE_BOARD
				 WHERE
				 		REF_BNO = #{boardNo}
			""")
	public void deleteBoardDelete(Long boardNo);

	
	@Select("""
				SELECT
						REF_BNO
				  FROM
				  		DELETE_BOARD
				 WHREE
				 		REF_BNO = #{boardNo}
			""")
	public Long findByDeleteBoardNo();
}