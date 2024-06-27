package com.emodi.emodibackv2.service;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Component
public class PasswordEncryptor {
	private static final int BCRYPT_SALT_LENGTH = 10;

	public String encryptPassword(String plainPassword) {
		BCrypt.Hasher hasher = BCrypt.withDefaults();
		byte[] hashedPasswordBytes = hasher.hash(
			BCRYPT_SALT_LENGTH,
			plainPassword.getBytes(StandardCharsets.UTF_8)
		);

		return new String(hashedPasswordBytes, StandardCharsets.UTF_8);
	}

	public boolean matchPassword(String plainPassword, String encryptedPassword) {
		BCrypt.Verifyer verifyer = BCrypt.verifyer();
		BCrypt.Result result = verifyer.verify(
			plainPassword.getBytes(StandardCharsets.UTF_8),
			encryptedPassword.getBytes(StandardCharsets.UTF_8)
		);

		return result.verified;
	}
}
