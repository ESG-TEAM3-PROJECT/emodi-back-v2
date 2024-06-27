package com.emodi.emodibackv2.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.emodi.emodibackv2.config.SentimentProperties;
import com.emodi.emodibackv2.service.dto.response.SentimentResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SentimentAnalyzer {
	private final SentimentProperties sentimentProperties;
	private final RestTemplate restTemplate;

	public SentimentResponse analyze(String content) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("X-NCP-APIGW-API-KEY-ID", sentimentProperties.getSentimentKey());
		httpHeaders.set("X-NCP-APIGW-API-KEY", sentimentProperties.getSentimentSecret());

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("content", content);

		HttpEntity<Map<String, String>> httpRequest = new HttpEntity<>(requestBody, httpHeaders);

		ResponseEntity<SentimentResponse> httpResponse = restTemplate.postForEntity(
			sentimentProperties.getSentimentUrl(),
			httpRequest,
			SentimentResponse.class);

		return httpResponse.getBody();
	}
}
