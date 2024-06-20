package com.lee.reviewmon.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lee.reviewmon.dto.CommentDto;
import com.lee.reviewmon.service.CommentService;

/*
 * 댓글 목록 불러올 땐 api 안 쓰고 ItemController.java 에서 CommentService.java 통해서 댓글목록 받아온 다음에 ItemController.java 에서 모델로 등록해서 사용
 */

@RestController
public class CommentApiController {
	@Autowired
	private CommentService commentService;
	
	// 댓글 조회
	@GetMapping("/api/items/{itemId}/comments")
	public ResponseEntity<List<CommentDto>> comments(@PathVariable("itemId") Long itemId) {
		// 서비스에 위임
		List<CommentDto> dtos = commentService.comments(itemId);
		
		// 결과 응답
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

}
