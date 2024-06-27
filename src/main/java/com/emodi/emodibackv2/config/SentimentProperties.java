package com.emodi.emodibackv2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class SentimentProperties {
	private String sentimentKey;
	private String sentimentSecret;
	private String sentimentUrl;
}
