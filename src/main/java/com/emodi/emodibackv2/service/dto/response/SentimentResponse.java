package com.emodi.emodibackv2.service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentimentResponse {
	@JsonProperty("document")
	private Document document;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Document {
		@JsonProperty("sentiment")
		private String sentiment;

		@JsonProperty("confidence")
		private Confidence confidence;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Confidence {
		@JsonProperty("neutral")
		private double neutral;

		@JsonProperty("positive")
		private double positive;

		@JsonProperty("negative")
		private double negative;
	}
}
