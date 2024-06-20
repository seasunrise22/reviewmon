package com.lee.reviewmon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lee.reviewmon.dto.CommentDto;
import com.lee.reviewmon.entity.Comment;
import com.lee.reviewmon.repository.CommentRepository;
import com.lee.reviewmon.repository.ItemRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ItemRepository itemRepository;
	
	// 댓글 조회
	public List<CommentDto> comments(Long itemId) {
		
		// for문 버전
//		// 리파지터리를 통해 댓글 가져오기
//		List<Comment> comments = commentRepository.findByItemId(itemId);
//		
//		// 엔티티 -> DTO 변환
//		List<CommentDto> dtos = new ArrayList<CommentDto>();
//		
//		for(int i=0; i<comments.size(); i++) {
//			Comment c = comments.get(i);
//			CommentDto dto = CommentDto.createCommentDto(c);
//			dtos.add(dto);
//		}
//		
//		// 결과 반환
//		return dtos;
		
		// 스트림 버전
		return commentRepository.findByItemId(itemId) // 댓글 엔티티 목록 조회
				.stream() // 댓글 엔티티 목록을 스트림으로 전환
				.map(comment -> CommentDto.createCommentDto(comment)) // 엔티티를 DTO로 매핑
				.collect(Collectors.toList()); // 스트림을 리스트로 변환
	}
}
