package com.emodi.emodibackv2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emodi.emodibackv2.entity.Diary;
import com.emodi.emodibackv2.entity.User;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
	Optional<Diary> findByIdAndAuthor(Long diaryId, User author);
}

