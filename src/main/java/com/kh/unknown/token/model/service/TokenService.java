package com.kh.unknown.token.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.unknown.auth.model.vo.CustomUserDetails;
import com.kh.unknown.exception.NotFoundException;
import com.kh.unknown.token.model.dao.TokenMapper;
import com.kh.unknown.token.model.vo.RefreshToken;
import com.kh.unknown.token.util.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
	private final JwtUtil tokenUtil;
	private final TokenMapper tokenMapper;
	
	public Map<String, String> getTokens(CustomUserDetails user) {
		/*
		 * String accessToken = tokenUtil.getAccessToken(user); log.info("이게토큰 : {} ",
		 * accessToken); String refreshToken = tokenUtil.getRefreshToken(user);
		 */
		// 리프레시 토큰은 DB에 저장
		Map<String, String> tokens = createTokens(user);
		saveToken(tokens.get("refreshToken"),user.getAdminNo());
		
		return tokens;
	}
	
	// 토큰을 만들어서 반환해주는 메소드
	private Map<String,String> createTokens(CustomUserDetails user) {
		return Map.of("accessToken", tokenUtil.getAccessToken(user),
						"refreshToken", tokenUtil.getRefreshToken(user));
	}
	
	// 리프레시토큰을 받아서 DB에 INSERT해주는 메소드
	private void saveToken(String token, Long adminNo) {
		log.info("token : {} , adminNo : {} ", token, adminNo);
		
		RefreshToken refreshToken = RefreshToken.builder()
										.adminNo(adminNo)
										.token(token)
										.expiration(System.currentTimeMillis()+ (1000*60*60*24*3))
										.build();
		tokenMapper.saveToken(refreshToken);
	}
	// 로그아웃 요청있을때 DB에있는거 싹 지워주는 메소드
	public void logout(Long adminNo) {
		tokenMapper.deleteToken(adminNo);
	}
	// 추후 AccessToken이 만료기간이 지나서 토큰 갱신 요청이 들어왔을때
	// 사용자에게 전달받은 RefreshToken이 DB에 존재하면서 만료기간이 지나지 않았는지 검증하는 메소드
	
	public Map<String, String> tokenRotation(String refreshToken){
		RefreshToken token = tokenMapper.findByToken(refreshToken);
		if(token == null || token.getExpiration() < System.currentTimeMillis()) {
			throw new NotFoundException("유효하지 않은 토큰입니다.");
		}
		Claims claims = tokenUtil.parseJwt(token.getToken());
		String adminNo = claims.getSubject();
		
		String adminName = (String)claims.get("AdminName");
		CustomUserDetails user = CustomUserDetails.builder().adminName(adminName).username(adminNo).build();
		return createTokens(user);
	}

	
}