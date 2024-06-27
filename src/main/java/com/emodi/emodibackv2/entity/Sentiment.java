package com.emodi.emodibackv2.entity;

import com.emodi.emodibackv2.entity.common.BaseTimeEntity;
import com.emodi.emodibackv2.service.dto.response.SentimentResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Sentiment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mood;

	private double neutral;

	private double positive;

	private double negative;

	public void update(SentimentResponse.Document document, SentimentResponse.Confidence confidence) {

		if (document == null || confidence == null) {
			throw new IllegalArgumentException("Sentiment 업데이트가 실패했습니다.");
		}

		this.mood = document.getSentiment();
		this.neutral = confidence.getNeutral();
		this.positive = confidence.getPositive();
		this.negative = confidence.getNegative();
	}
}
