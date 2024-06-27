package com.emodi.emodibackv2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emodi.emodibackv2.entity.User;
import com.emodi.emodibackv2.jwt.JwtProvider;
import com.emodi.emodibackv2.service.AuthService;
import com.emodi.emodibackv2.service.dto.request.LoginInfoRequest;
import com.emodi.emodibackv2.service.dto.request.SignupInfoRequest;
import com.emodi.emodibackv2.service.dto.response.UserIdDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final JwtProvider jwtProvider;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody SignupInfoRequest request) {
		authService.signUp(request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body("회원가입이 완료되었습니다.");
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginInfoRequest request, HttpServletResponse response) {
		User user = authService.login(request);

		String jwt = jwtProvider.generateToken(user.getId());
		Cookie cookie = new Cookie("jwt", jwt);
		cookie.setPath("/");
		response.addCookie(cookie);

		return ResponseEntity.status(HttpStatus.OK)
			.body("로그인이 완료되었습니다.");
	}

	@GetMapping("/me")
	public ResponseEntity<UserIdDto> getUserId(@CookieValue("jwt") String token) {
		Long userId = jwtProvider.verifyToken(token);
		return ResponseEntity.status(HttpStatus.OK)
			.body(new UserIdDto(userId));
	}
}
