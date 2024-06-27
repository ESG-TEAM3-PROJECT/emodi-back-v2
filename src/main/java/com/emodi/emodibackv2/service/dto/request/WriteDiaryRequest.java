package com.emodi.emodibackv2.service.dto.request;

import java.time.LocalDate;

import com.emodi.emodibackv2.entity.Diary;
import com.emodi.emodibackv2.entity.Sentiment;
import com.emodi.emodibackv2.entity.User;

public record WriteDiaryRequest(
	String date,
	String title,
	String content
) {
	public Diary toDiary(User user, Sentiment sentiment) {
		return Diary.builder()
			.date(LocalDate.parse(date))
			.title(title)
			.content(content)
			.author(user)
			.sentiment(sentiment)
			.build();
	}
}
