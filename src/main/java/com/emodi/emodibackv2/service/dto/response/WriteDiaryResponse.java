package com.emodi.emodibackv2.service.dto.response;

import com.emodi.emodibackv2.entity.Diary;

public record WriteDiaryResponse(
	String message,
	DairyDto dairyDto,
	SentimentDto sentimentDto
) {
	public static WriteDiaryResponse toWriteDiaryResponse(String message, Diary dairy) {
		return new WriteDiaryResponse(
			message,
			DairyDto.toDiaryDto(dairy),
			SentimentDto.toSentimentDto(dairy.getSentiment())
		);
	}
}
