package com.emodi.emodibackv2.service.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.emodi.emodibackv2.entity.Diary;

public record DairyDto(
	Long diaryId,
	Long authorId,
	String title,
	String content,
	LocalDate date,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static DairyDto toDiaryDto(Diary diary) {
		return new DairyDto(
			diary.getId(),
			diary.getAuthor().getId(),
			diary.getTitle(),
			diary.getContent(),
			diary.getDate(),
			diary.getCreatedAt(),
			diary.getUpdatedAt()
		);
	}
}
