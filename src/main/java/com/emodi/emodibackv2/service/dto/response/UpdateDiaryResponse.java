package com.emodi.emodibackv2.service.dto.response;

import com.emodi.emodibackv2.entity.Diary;

public record UpdateDiaryResponse(
	String message,
	DairyDto dairyDto,
	SentimentDto sentimentDto
) {
	public static UpdateDiaryResponse toUpdateDiaryResponse(String message, Diary dairy) {
		return new UpdateDiaryResponse(
			message,
			DairyDto.toDiaryDto(dairy),
			SentimentDto.toSentimentDto(dairy.getSentiment())
		);
	}
}
