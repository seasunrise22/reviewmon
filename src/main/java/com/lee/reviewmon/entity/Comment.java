package com.lee.reviewmon.entity;

import com.lee.reviewmon.dto.CommentDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "item_id") // 외래키 생성, Item 엔티티의 기본키(id)와 매핑
	private Item item;
	@Column
	private String nickname;
	@Column
	private String body;
	@Column
	private Float star; // Float은 float과 다르게 null 처리 가능
	
	

	public Comment() {
	}

	public Comment(Long id, Item item, String nickname, String body, Float star) {
		this.id = id;
		this.item = item;
		this.nickname = nickname;
		this.body = body;
		this.star = star;
	}

	// 댓글 생성 시 댓글 엔티티 생성 함수
	public static Comment createComment(CommentDto dto, Item item) {
		// 예외 발생
		// 댓글 생성도 전에 id가 존재할 수는 없으므로. 댓글은 DB에 넣어야 DB에서 id가 생성이 된다.
		if (dto.getId() != null)
			throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");

		// 이건 뭐 그냥 rest api 요청할 때 id값 다르게 하면 에러 나도록
		if (dto.getItemId() != item.getId())
			throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");

		// 엔티티 생성 및 반환
		return new Comment(dto.getId(), item, dto.getNickname(), dto.getBody(), dto.getStar());
	}
	
	// 댓글 수정 함수
	public void patch(CommentDto dto) {
		// 예외 발생
		if(this.id != dto.getId())
			throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
		
		// 객체 갱신
		if(dto.getNickname() != null)
			this.nickname = dto.getNickname();
		if(dto.getBody() != null)
			this.body = dto.getBody();
		if(dto.getStar() != null)
			this.star = dto.getStar();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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
