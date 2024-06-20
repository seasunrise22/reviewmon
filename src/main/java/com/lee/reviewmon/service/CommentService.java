package com.lee.reviewmon.service;

import java.util.ArrayList;
import java.util.List;

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
		System.out.println("itemId is = " + itemId);
		
		// 리파지터리를 통해 댓글 가져오기
		List<Comment> comments = commentRepository.findByItemId(itemId);
		
		// 엔티티 -> DTO 변환
		List<CommentDto> dtos = new ArrayList<CommentDto>();
		for(int i=0; i<comments.size(); i++) {
			Comment c = comments.get(i);
			CommentDto dto = CommentDto.createCommentDto(c);
			dtos.add(dto);
		}
		
		// 결과 반환
		return dtos;
	}
}
