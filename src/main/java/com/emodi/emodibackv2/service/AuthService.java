package com.emodi.emodibackv2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emodi.emodibackv2.entity.User;
import com.emodi.emodibackv2.repository.UserRepository;
import com.emodi.emodibackv2.service.dto.request.LoginInfoRequest;
import com.emodi.emodibackv2.service.dto.request.SignupInfoRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncryptor passwordEncryptor;

	public User signUp(SignupInfoRequest request) {
		if (userRepository.existsByUsername(request.username())) {
			throw new IllegalArgumentException("이미 존재하는 회원입니다.");
		}

		String encryptedPassword = passwordEncryptor.encryptPassword(request.password());
		User user = request.toUser(encryptedPassword);
		return userRepository.save(user);
	}

	public User login(LoginInfoRequest request) {
		User user = userRepository.findByUsername(request.username())
			.orElseThrow(() -> new IllegalArgumentException("회원가입이 필요합니다."));

		if (!passwordEncryptor.matchPassword(request.password(), user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		return user;
	}
}
