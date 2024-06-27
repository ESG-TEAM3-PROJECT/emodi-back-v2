package com.emodi.emodibackv2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emodi.emodibackv2.entity.Diary;
import com.emodi.emodibackv2.jwt.JwtProvider;
import com.emodi.emodibackv2.service.DiaryService;
import com.emodi.emodibackv2.service.dto.request.UpdateDiaryRequest;
import com.emodi.emodibackv2.service.dto.request.WriteDiaryRequest;
import com.emodi.emodibackv2.service.dto.response.GetDiaryResponse;
import com.emodi.emodibackv2.service.dto.response.UpdateDiaryResponse;
import com.emodi.emodibackv2.service.dto.response.WriteDiaryResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DiaryController {
	private final DiaryService diaryService;
	private final JwtProvider jwtProvider;

	@PostMapping("/diaries")
	public ResponseEntity<WriteDiaryResponse> writeDiary(
		@CookieValue("jwt") String token,
		@RequestBody WriteDiaryRequest request
	) {
		Long userId = jwtProvider.verifyToken(token);

		Diary diary = diaryService.writeDiary(userId, request);

		WriteDiaryResponse writeDiaryResponse = WriteDiaryResponse.toWriteDiaryResponse(
			"일기 작성이 완료되었습니다.",
			diary
		);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(writeDiaryResponse);
	}

	@PutMapping("/diaries/{diaryId}")
	public ResponseEntity<UpdateDiaryResponse> updateDiary(
		@CookieValue("jwt") String token,
		@PathVariable("diaryId") Long diaryId,
		@RequestBody UpdateDiaryRequest request
	) {
		Long userId = jwtProvider.verifyToken(token);
		Diary diary = diaryService.updateDiary(userId, diaryId, request);

		UpdateDiaryResponse updateDiaryResponse = UpdateDiaryResponse.toUpdateDiaryResponse(
			"일기 수정이 완료되었습니다.",
			diary
		);

		return ResponseEntity.status(HttpStatus.OK)
			.body(updateDiaryResponse);
	}

	@GetMapping("/diaries/{diaryId}")
	public ResponseEntity<GetDiaryResponse> getDiary(
		@CookieValue("jwt") String token,
		@PathVariable("diaryId") Long diaryId
	) {
		Long userId = jwtProvider.verifyToken(token);
		Diary diary = diaryService.getDiary(userId, diaryId);

		GetDiaryResponse getDiaryResponse = GetDiaryResponse.toGetDiaryResponse(
			"일기 조회를 완료했습니다.",
			diary
		);

		return ResponseEntity.status(HttpStatus.OK)
			.body(getDiaryResponse);
	}

	@DeleteMapping("/diaries/{diaryId}")
	public ResponseEntity<String> deleteDiary(
		@CookieValue("jwt") String token,
		@PathVariable("diaryId") Long diaryId
	) {
		Long userId = jwtProvider.verifyToken(token);
		diaryService.deleteDiary(userId, diaryId);

		return ResponseEntity.status(HttpStatus.OK)
			.body("일기 삭제가 완료되었습니다.");
	}
}
