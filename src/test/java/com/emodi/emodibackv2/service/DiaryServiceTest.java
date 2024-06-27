package com.emodi.emodibackv2.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.emodi.emodibackv2.entity.Diary;
import com.emodi.emodibackv2.entity.User;
import com.emodi.emodibackv2.repository.UserRepository;
import com.emodi.emodibackv2.service.dto.request.WriteDiaryRequest;

@SpringBootTest
class DiaryServiceTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DiaryService diaryService;
	private User user;

	@BeforeEach
	void setUp() {
		user = User.builder()
			.id(1L)
			.build();

		userRepository.save(user);
	}

	@Test
	public void 다이어리_생성_성공() {
		// given
		WriteDiaryRequest request = new WriteDiaryRequest("2024-06-17", "햅삐햅삐", "재미있다");

		// when
		Diary diary = diaryService.writeDiary(1L, request);

		// then
		assertThat(diary.getId()).isEqualTo(1L);
		assertThat(diary.getAuthor().getId()).isEqualTo(user.getId());
		assertThat(diary.getSentiment().getMood()).isEqualTo("positive");
	}
}
