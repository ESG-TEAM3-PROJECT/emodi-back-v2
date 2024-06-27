package com.emodi.emodibackv2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emodi.emodibackv2.entity.Diary;
import com.emodi.emodibackv2.entity.Sentiment;
import com.emodi.emodibackv2.entity.User;
import com.emodi.emodibackv2.repository.DiaryRepository;
import com.emodi.emodibackv2.repository.UserRepository;
import com.emodi.emodibackv2.service.dto.request.UpdateDiaryRequest;
import com.emodi.emodibackv2.service.dto.request.WriteDiaryRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
	private final UserRepository userRepository;
	private final DiaryRepository diaryRepository;
	private final SentimentService sentimentService;

	public Diary writeDiary(Long userId, WriteDiaryRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		Sentiment sentiment = sentimentService.analyze(request);

		Diary diary = request.toDiary(user, sentiment);

		return diaryRepository.save(diary);

	}

	public Diary updateDiary(
		Long userId,
		Long diaryId,
		UpdateDiaryRequest request
	) {
		Diary diary = diaryRepository.findById(diaryId)
			.orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));

		User author = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		sentimentService.reAnalyze(diaryId, request);

		diary.updateDiary(author, request);

		return diary;
	}

	public Diary getDiary(Long userId, Long diaryId) {
		User author = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		return diaryRepository.findByIdAndAuthor(diaryId, author)
			.orElseThrow(() -> new IllegalArgumentException("사용자의 일기를 찾을 수 없습니다."));
	}

	public void deleteDiary(Long userId, Long diaryId) {
		User author = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		Diary foundDiary = diaryRepository.findByIdAndAuthor(diaryId, author)
			.orElseThrow(() -> new IllegalArgumentException("사용자의 일기를 찾을 수 없습니다."));

		diaryRepository.delete(foundDiary);
	}
}
