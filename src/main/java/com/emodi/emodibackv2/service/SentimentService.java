package com.emodi.emodibackv2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emodi.emodibackv2.entity.Diary;
import com.emodi.emodibackv2.entity.Sentiment;
import com.emodi.emodibackv2.repository.DiaryRepository;
import com.emodi.emodibackv2.repository.SentimentRepository;
import com.emodi.emodibackv2.service.dto.request.UpdateDiaryRequest;
import com.emodi.emodibackv2.service.dto.request.WriteDiaryRequest;
import com.emodi.emodibackv2.service.dto.response.SentimentResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SentimentService {
	private final SentimentAnalyzer sentimentAnalyzer;
	private final SentimentRepository sentimentRepository;
	private final DiaryRepository diaryRepository;

	public Sentiment analyze(WriteDiaryRequest request) {
		SentimentResponse sentimentResponse = sentimentAnalyzer.analyze(request.content());
		SentimentResponse.Document document = sentimentResponse.getDocument();
		SentimentResponse.Confidence confidence = document.getConfidence();

		Sentiment sentiment = Sentiment.builder()
			.mood(document.getSentiment())
			.neutral(confidence.getNeutral())
			.positive(confidence.getPositive())
			.negative(confidence.getNegative())
			.build();

		return sentimentRepository.save(sentiment);
	}

	public void reAnalyze(Long diaryId, UpdateDiaryRequest request) {
		SentimentResponse sentimentResponse = sentimentAnalyzer.analyze(request.content());
		SentimentResponse.Document document = sentimentResponse.getDocument();
		SentimentResponse.Confidence confidence = document.getConfidence();

		Diary foundDiary = diaryRepository.findById(diaryId)
			.orElseThrow(() -> new IllegalArgumentException("[SentimentService] 일기를 찾을 수 없습니다."));

		Sentiment currentSentiment = foundDiary.getSentiment();
		currentSentiment.update(document, confidence);

		foundDiary.updateSentiment(currentSentiment);
	}
}
