package com.kh.unknown.configuration.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import com.kh.unknown.auth.model.vo.CustomUserDetails;
import com.kh.unknown.token.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailService;

	// 안에서 반환하는 경로들을 필터를 아예 안타게해주는 메소드
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String uri = request.getRequestURI();
		return uri.equals("/api/auth/login") || uri.equals("/api/auth/refresh");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		request.getUserPrincipal();
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorization == null || !authorization.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		//log.info("이거 어떻게 올까? : {}", authorization);
		/*
		 * String uri = request.getRequestURI(); if(uri.equals("/api/auth/login")) {
		 * filterChain.doFilter(request, response); return; }
		 */

		String token = authorization.substring(7);
		try {
			Claims claims = jwtUtil.parseJwt(token);
			String username = claims.getSubject();
			CustomUserDetails user = (CustomUserDetails)userDetailService.loadUserByUsername(username);
			//log.info("로그 {} ", user);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			// 세부 설정 관련들 사용자의 IP주소, MAC주소, sessionId등을 포함시켜서 셋팅
			SecurityContextHolder.getContext().setAuthentication(authentication);
			// 이렇게 담아주면 현재 요청이 만료될 때까지 Authentication에 담겨있는 사용자의 정보를 사용할수 있음
			
			//log.info("이토큰의 소유주의 PK : {} ", username);
		} catch (ExpiredJwtException e) {
			log.info("토큰의 유효기간 만료");
			response.setStatus(401);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(String.format("토큰만료"));
			return;
		} catch (JwtException e) {
			log.info("이 서버의 서비스키로 만든 토큰이 아님");
			response.setStatus(401);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(String.format("유효하지 않은 토큰"));
		}
		filterChain.doFilter(request, response);
		
	}

}