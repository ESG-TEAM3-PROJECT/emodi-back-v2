package com.emodi.emodibackv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emodi.emodibackv2.entity.Sentiment;

public interface SentimentRepository extends JpaRepository<Sentiment, Long> {
}
