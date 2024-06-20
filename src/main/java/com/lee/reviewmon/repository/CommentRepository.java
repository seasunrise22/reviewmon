package com.lee.reviewmon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lee.reviewmon.entity.Comment;

// JPA에서 단순히 CRUD 작업만 한다면 CrudRepository를 사용해도 충분
// 하지만 CRUD 작업에 더해 페이지 처리와 정렬 작업까지 해야 한다면 JpaRepository를 사용하는 것이 좋음
public interface CommentRepository extends CrudRepository<Comment, Long> {
	
	// 특정 게시글의 모든 댓글 조회
	@Query(value = "SELECT * FROM comment WHERE item_id = :itemId", nativeQuery = true)
	List<Comment> findByItemId(@Param("itemId") Long itemId);

}
