package com.emodi.emodibackv2.service.dto.request;

import com.emodi.emodibackv2.entity.User;

public record SignupInfoRequest(
	String username,
	String password
) {
	public User toUser(String encryptedPassword) {
		return User.builder()
			.username(username)
			.password(encryptedPassword)
			.build();
	}
}
