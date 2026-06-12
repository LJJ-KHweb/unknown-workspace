package com.kh.unknown.token.model.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.unknown.token.model.vo.RefreshToken;

@Mapper
public interface TokenMapper {

	@Insert("INSERT INTO ADMIN_TOKEN VALUES (#{adminNo}, #{token}, #{expiration})")
	void saveToken(RefreshToken token);
	
	@Delete("DELETE FROM ADMIN_TOKEN WHERE MEMBER_ID = #{memberId}")
	void deleteToken(String memberId);
	
	@Select("SELECT MEMBER_ID, TOKEN, EXPIRATION FROM ADMIN WHERE TOKEN = #{token}")
	RefreshToken findByToken(String token);
}