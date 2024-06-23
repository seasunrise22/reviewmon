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
## 1. 게시판

### 게시글 목록

![게시글리스트](https://github.com/seasunrise22/spring_crud_study/assets/45503931/19d73d93-723e-4ded-bf17-3bb3c8405e14)

### 게시글 작성

![게시글작성](https://github.com/seasunrise22/spring_crud_study/assets/45503931/28baf19f-dcdc-4bb5-b861-1d98e4b512fb)

### 게시글 열람

![게시글열람1](https://github.com/seasunrise22/spring_crud_study/assets/45503931/9ecf97ab-43c7-44ae-a6c4-905370e59268)
![게시글열람2](https://github.com/seasunrise22/spring_crud_study/assets/45503931/8c54be8f-a4fc-4f9b-8dc0-8ef23e7f1c27)

### 게시글 수정

![게시글수정1](https://github.com/seasunrise22/spring_crud_study/assets/45503931/61e9cc92-d588-4cab-81f2-a1c6137419a9)
![게시글수정2](https://github.com/seasunrise22/spring_crud_study/assets/45503931/1d81716a-92e3-4935-b209-83f9a923ee7b)
![게시글수정3](https://github.com/seasunrise22/spring_crud_study/assets/45503931/1c921586-ec98-4187-a722-bccfb98d0f30)
![게시글수정4](https://github.com/seasunrise22/spring_crud_study/assets/45503931/4070f856-30f5-4037-8783-209c2912d8ea)

### 게시글 삭제

![게시글삭제1](https://github.com/seasunrise22/spring_crud_study/assets/45503931/ecf1627e-bfdf-4c5c-850b-29f332bb4924)
![게시글삭제2](https://github.com/seasunrise22/spring_crud_study/assets/45503931/6875a390-d389-42e4-b084-240042ee54b0)

### 페이지네이션

![페이지1](https://github.com/seasunrise22/spring_crud_study/assets/45503931/eff3fffe-3ed1-4e57-a10e-97017eb2f50d)
![페이지2](https://github.com/seasunrise22/spring_crud_study/assets/45503931/38102005-db7a-4e4c-8df6-080b15ba4a33)
![페이지3](https://github.com/seasunrise22/spring_crud_study/assets/45503931/4b2a7869-dde1-4bf2-8c12-f98907d1a9bf)
![페이지4](https://github.com/seasunrise22/spring_crud_study/assets/45503931/b67667db-523a-4ee8-a34b-0c91138d1f8d)

## 2. 댓글

### 댓글 목록

![댓글목록](https://github.com/seasunrise22/spring_crud_study/assets/45503931/855ff8c6-8826-488c-af8c-eb4ac7615826)

### 댓글 작성

![댓글작성1](https://github.com/seasunrise22/spring_crud_study/assets/45503931/5073c543-ed3b-48cf-9470-e1e503a200d6)
![댓글작성2](https://github.com/seasunrise22/spring_crud_study/assets/45503931/654f5ae2-6182-46a3-9ed8-273a35455131)

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
