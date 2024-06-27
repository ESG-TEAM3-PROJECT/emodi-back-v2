package com.emodi.emodibackv2.service.dto.response;

import com.emodi.emodibackv2.entity.Diary;

public record GetDiaryResponse(
	String message,
	DairyDto dairyDto,
	SentimentDto sentimentDto
) {
	public static GetDiaryResponse toGetDiaryResponse(String message, Diary dairy) {
		return new GetDiaryResponse(
			message,
			DairyDto.toDiaryDto(dairy),
			SentimentDto.toSentimentDto(dairy.getSentiment())
		);
	}
}
