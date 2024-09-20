package com.lee.reviewmon.api;

import java.util.List;

import com.lee.reviewmon.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lee.reviewmon.dto.CommentDto;
import com.lee.reviewmon.service.CommentService;

/*
 * 실제 웹페이지에서 댓글 기능을 전부 api 경유하진 않고 경유하는 것도 있고 바로 댓글 서비스 통해서 하는 것도 있음
 * API 요청 통해서: 댓글 생성, 수정
 * ItemController에서 CommentService 통해서: 댓글 목록 조회
 */

@RestController
public class CommentApiController {
	@Autowired
	private CommentService commentService;

	/*
	 * 실제 댓글목록 조회는 api 경유하지 않고 CommentService 통해서 출력
	 */
	// 댓글 조회
	@GetMapping("/api/items/{itemId}/comments")
	public ResponseEntity<List<CommentDto>> comments(@PathVariable("itemId") Long itemId) {
		// 서비스에 위임
		List<CommentDto> dtos = commentService.comments(itemId);

		// 결과 응답
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

	// 댓글 생성(fetch 통해서 api 경유)
	@PostMapping("/api/items/{itemId}/comments")
	public ResponseEntity<CommentDto> create(@PathVariable("itemId") Long itemId, @RequestBody CommentDto dto) {
		CommentDto createdDto = commentService.create(itemId, dto);
		return ResponseEntity.status(HttpStatus.OK).body(createdDto);
	}

	// 댓글 수정(fetch 통해서 api 경유)
	@PatchMapping("/api/comments/{id}")
	public ResponseEntity<CommentDto> update(@PathVariable("id") Long id, @RequestBody CommentDto dto) {
		CommentDto updatedDto = commentService.update(id, dto);
		return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
	}

	// 댓글 삭제(fetch 통해서 api 경유)
	@DeleteMapping("/api/comments/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		System.out.println("delete id is: " + id);
		commentService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
