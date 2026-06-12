package com.kh.unknown.admin.model.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.unknown.admin.model.vo.Admin;
import com.kh.unknown.auth.model.dto.LoginRequestDto;

@Mapper
public interface AdminMapper {
	
	@Select("SELECT ADMIN_NO FROM ADMIN WHERE ADMIN_ID = #{adminId} AND ADMIN_PWD = #{adminPwd} AND STATUS ='Y")
	public Long findByAdminNo(LoginRequestDto lrd);
	
	@Insert("INSERT INTO ADMIN VALUES(SEQ_AN.NEXTVAL, #{adminId}, #{adminPwd}, #{adminName}, 'ROLE_ADMIN', SYSDATE, 'Y')")
	int signup(Admin admin);
	
	@Select("SELECT COUNT(*) FROM ADMIN WHERE ADMIN_ID = #{adminId}")
	int findAdminIdCount(String adminId);
	
	
}