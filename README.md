# reviewmon
- 개발인원 : 1명
- 역할
  - 전체
## Introduction
**Spring Framework**와 **MVC 패턴**의 학습을 위해 영화 리뷰 사이트를 구현해보았습니다.

데이터베이스는 개발용 데이터베이스인 **H2 Database**를 사용하였고, 데이터베이스 연동은 **JPA Repository** 를 이용하여 객체지향적으로 하였습니다.

서버는 Spring Boot **내장 Tomcat서버**를 사용하였습니다.

구현한 기능은 아래와 같습니다.

1. 작품 목록 등록, 조회, 수정, 삭제 기능
2. 작품별 댓글 등록, 조회, 수정, 삭제 기능
## Development Environment
- Spring Boot(3.3.1) / start.spring.io 에서 프로젝트 생성했습니다.
- STS4(4.23.1)
- Eclipse IDE(2024-06)
- Adoptium JDK (17.0.11.9)
## Screenshots
### 1. 작품 목록 CRUD

![index 화면 작품 등록 전](https://github.com/seasunrise22/reviewmon/assets/45503931/c27959b6-694e-4c49-befa-57c7b31164b4)
---
![index 화면 작품 등록 중](https://github.com/seasunrise22/reviewmon/assets/45503931/fb471856-09b0-416a-bfd2-5f826bb3f319)
---
![index 화면 작품 등록 후](https://github.com/seasunrise22/reviewmon/assets/45503931/8218d62f-a182-4fe5-b5ec-b15a5e08af5e)
---
![show 화면 작품 등록 후](https://github.com/seasunrise22/reviewmon/assets/45503931/42a0db7c-3178-43bb-b1bb-dcdb8d2a3c5e)

### 작품별 댓글 CRUD

![(댓글) show 화면 댓글 등록 전](https://github.com/seasunrise22/reviewmon/assets/45503931/0013ea67-414a-4628-bf25-b80fe7a66bf7)
---
![(댓글) show 화면 댓글 등록 후](https://github.com/seasunrise22/reviewmon/assets/45503931/71cfaed1-6836-48c3-977c-a800e14fcc34)
---
![(댓글) show 화면 댓글 수정 전](https://github.com/seasunrise22/reviewmon/assets/45503931/b419bf10-e355-4943-99b1-3e2d948ff412)
---
![(댓글) show 화면 댓글 수정 후](https://github.com/seasunrise22/reviewmon/assets/45503931/b369e69e-401c-45e3-80ae-52445b62e5be)

## Code Preview
## 프로그램의 전반적인 동작 흐름

### MVC 패턴 구조

![디렉터리1](https://github.com/seasunrise22/spring_crud_study/assets/45503931/687ff830-b4a6-4906-b222-dbc467bb8810)

MVC 패턴에 따라 프로젝트를 모델(Model), 뷰(View), 컨트롤러(Controller)로 계층화 하였습니다.

게시글을 열람하는 과정을 통해 프로젝트의 동작 흐름을 알아보겠습니다.

### 뷰(View)

게시글 목록(list.jsp)에서 특정 게시글을 클릭하면 서버의 /read 경로에 게시글 id 값을 붙여서 전송합니다.

```jsp
<c:forEach var="post" items="${posts}">
				<div class="row">
					<div class="cell">${post.id}</div>
					<div class="cell">
						<a href="/read/${post.id}">${post.title}</a>
					</div>
					<div class="cell">${post.author}</div>
					<div class="cell">${post.createdAt}</div>
					<div class="cell">${post.views}</div>
				</div>
			</c:forEach>
```

### 컨트롤러(Controller)
사용자의 요청을 처리하고 뷰와 모델 사이에서 적절한 중재역할을 하기 위한 컨트롤러인 BoardController.java 파일의 일부입니다.

넘어온 게시글의 id 값을 **@PathVariable** 로 받아 서비스의 getPostById 메서드로 넘겨주어 게시글에 대한 데이터를 가져오게 합니다.

이후 받아온 게시글 데이터는 **model.addAttribute("post", boardService.getPostById(id));** 를 통해 post 라는 이름으로 뷰에서 사용할 수 있습니다.

```java
@GetMapping("/read/{id}")
	public String readPost(@PathVariable Integer id, Model model) {
		boardService.incrementViews(id); // 조회수 증가

		model.addAttribute("post", boardService.getPostById(id));
		model.addAttribute("comments", boardService.getCommentsById(id));
		return "board/read";
	}
```

### 모델(Model) - 모델 객체
데이터를 표현하고 전송하고 처리하기 위한 모델 객체가 미리 정의되어 있어야 합니다.

아래는 게시글 모델 객체인 Post.java 의 일부입니다.

```java
package com.springstudy.models;

import java.util.Date;

public class Post {
	private Integer id; // 데이터베이스 자동 증가
	private String author;
	private String password;
	private String title;
	private String content;
	private Date createdAt; // 데이터베이스 자동 할당
	private Date updatedAt; // 데이터베이스 자동 할당
	private int views;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
```

### 모델(Model) - 서비스
컨트롤러로부터 호출 된 서비스객체 입니다.

서비스객체 에서는 조회수 증가 같은 비즈니스 로직을 담당하며, 데이터베이스와 직접적인 상호작용을 하는 객체와 연결시켜 줍니다.

```java
public Post getPostById(Integer id) {
		return boardDAO.getPostById(id);
	}

public void incrementViews(Integer id) {
		Post post = boardDAO.getPostById(id);
		post.incrementViews();
		boardDAO.updateViews(post);
	}
```

### 모델(Model) - DAO(Data Access Object)
데이터베이스와 직접적으로 상호작용하는 DAO객체의 일부입니다.

DAO 에서는 쿼리문을 통해 직접적으로 데이터베이스에서 데이터를 가져옵니다.

```java
public Post getPostById(Integer id) {
		Post post = null;
		try(Connection conn = dataSource.getConnection()) {
			String sql = "SELECT * FROM posts WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				post = new Post();
				post.setId(rs.getInt("id"));
				post.setPassword(rs.getString("password"));
				post.setAuthor(rs.getString("author"));
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setCreatedAt(rs.getDate("createdAt"));
				post.setUpdatedAt(rs.getDate("updatedAt"));
				post.setViews(rs.getInt("views"));				;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return post;
	}
```

이 프로젝트의 거의 모든 동작은 위와 같은 과정들을 통해 이루어집니다.
