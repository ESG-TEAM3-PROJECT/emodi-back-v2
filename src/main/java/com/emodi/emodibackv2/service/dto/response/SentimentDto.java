package com.emodi.emodibackv2.service.dto.response;

import com.emodi.emodibackv2.entity.Sentiment;

public record SentimentDto(
	String mood,
	double neutral,
	double positive,
	double negative
) {
	public static SentimentDto toSentimentDto(Sentiment sentiment) {
		return new SentimentDto(
			sentiment.getMood(),
			sentiment.getNeutral(),
			sentiment.getPositive(),
			sentiment.getNegative()
		);
	}
}
