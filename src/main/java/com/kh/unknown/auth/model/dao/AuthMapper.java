package com.kh.unknown.auth.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.unknown.admin.model.dto.AdminDto;

@Mapper
public interface AuthMapper {

	@Select("""
				SELECT
						ADMIN_NO
					 ,	ADMIN_ID
					 ,	ADMIN_PWD
					 ,	ADMIN_NAME
					 ,	ROLE
					 ,	STATUS
				  FROM
				  		ADMIN
				 WHERE
				 		STATUS = 'Y'
				   AND
				   		ADMIN_ID = #{username}
			""")
	AdminDto loadUser(String username);
	
}