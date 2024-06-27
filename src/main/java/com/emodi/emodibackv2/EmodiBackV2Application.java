package com.emodi.emodibackv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class EmodiBackV2Application {

	public static void main(String[] args) {
		SpringApplication.run(EmodiBackV2Application.class, args);
	}

}
