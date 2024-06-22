package com.lee.reviewmon.dto;

import com.lee.reviewmon.entity.Comment;

public class CommentDto {
	private Long id;
	private Long itemId;
	private String nickname;
	private String body;
	private Float star; // Float은 float과 다르게 null 처리 가능

	public CommentDto() {
	}

	public CommentDto(Long id, Long itemId, String nickname, String body, Float star) {
		this.id = id;
		this.itemId = itemId;
		this.nickname = nickname;
		this.body = body;
		this.star = star;
	}

	public static CommentDto createCommentDto(Comment comment) {
		return new CommentDto(comment.getId(), comment.getItem().getId(), comment.getNickname(), comment.getBody(),
				comment.getStar());
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

	public Float getStar() {
		return star;
	}

	public void setStar(Float star) {
		this.star = star;
	}

}
