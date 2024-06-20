package com.lee.reviewmon.dto;

import com.lee.reviewmon.entity.Comment;

public class CommentDto {
	private Long id;
	private Long itemId;
	private String nickname;
	private String body;
	private float star;
	


	public CommentDto(Long id, Long itemId, String nickname, String body, float star) {
		this.id = id;
		this.itemId = itemId;
		this.nickname = nickname;
		this.body = body;
		this.star = star;
	}



	public static CommentDto createCommentDto(Comment comment) {
		return new CommentDto(
				comment.getId(),
				comment.getItem().getId(),
				comment.getNickname(),
				comment.getBody(),
				comment.getStar()
				);
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getItemId() {
		return itemId;
	}



	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}



	public String getNickname() {
		return nickname;
	}



	public void setNickname(String nickname) {
		this.nickname = nickname;
	}



	public String getBody() {
		return body;
	}



	public void setBody(String body) {
		this.body = body;
	}



	public float getStar() {
		return star;
	}



	public void setStar(float star) {
		this.star = star;
	}
	
	
}
