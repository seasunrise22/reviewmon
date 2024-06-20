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
