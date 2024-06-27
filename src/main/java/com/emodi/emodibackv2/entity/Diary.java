package com.emodi.emodibackv2.entity;

import java.time.LocalDate;

import com.emodi.emodibackv2.entity.common.BaseTimeEntity;
import com.emodi.emodibackv2.service.dto.request.UpdateDiaryRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class Diary extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate date;

	private String title;

	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private User author;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "sentiment_id")
	private Sentiment sentiment;

	public void updateDiary(User author, UpdateDiaryRequest request) {
		boolean isCorrectAuthor = this.author.equals(author);

		if (!isCorrectAuthor) {
			throw new IllegalArgumentException("해당 사용자의 일기가 아닙니다.");
		}

		if (request.title() != null) {
			this.title = request.title();
		}

		if (request.content() != null) {
			this.content = request.content();
		}
	}

	public void updateSentiment(Sentiment sentiment) {
		if (sentiment == null) {
			throw new IllegalArgumentException("감정 분석 업데이트가 실패했습니다.");
		}
		this.sentiment = sentiment;
	}
}
