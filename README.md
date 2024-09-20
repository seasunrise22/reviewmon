# ReviewMon
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
- Spring Boot(3.3.1)
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

![(댓글) show 화면 댓글 등록 전](https://github.com/seasunrise22/reviewmon/assets/45503931/80388db7-89dd-4408-b814-d5303e1b3972)
---
![(댓글) show 화면 댓글 등록 후](https://github.com/seasunrise22/reviewmon/assets/45503931/6e737b55-a642-4755-bf9d-cf55fbde44d7)
---
![(댓글) show 화면 댓글 수정 전](https://github.com/seasunrise22/reviewmon/assets/45503931/5ad9ac80-c93c-4109-aa23-28b86a20281d)
---
![(댓글) show 화면 댓글 수정 후](https://github.com/seasunrise22/reviewmon/assets/45503931/a993ce22-6d67-42c7-901e-6f6f5f613223)

## 프로그램의 전반적인 동작 흐름

### 주요 로직
#### 작품 등록
![(로직) 댓글 등록 로직](https://github.com/seasunrise22/reviewmon/assets/45503931/c945b2f5-c05e-4bab-aa38-d473539d0012)
#### 작품별 댓글 등록
![(로직)작품 등록 로직](https://github.com/seasunrise22/reviewmon/assets/45503931/a269a510-5200-4cfa-8105-4a5f9632693f)
#### 프로젝트 구조

![리뷰몬코드구조](https://github.com/user-attachments/assets/e7adc3d5-094b-4e9c-b29f-16b34f717d06)

## Code Preview
### 작품 목록 조회
"/" 경로로 요청이 들어오면 컨트롤러에서 ItemService 객체의 getAll 메서드를 호출해 전체 작품 목록 리스트를 받아옵니다.

받아온 작품 목록 리스트는 model.addAttribute 으로 모델에 등록해 뷰 페이지에서 받아볼 수 있게 합니다.
```java
@Controller
public class ItemController {
	@Autowired
	ItemService itemService;
	@Autowired
	CommentService commentService;

	// 전체 작품 조회
	@GetMapping("/")
	public String index(Model model) {
		// 1. 서비스에서 모든 Item 데이터 가져오기
		List<Item> itemEntityLists = itemService.getAll();

		// 2. 가져온 Item 묶음을 모델에 등록하기
		model.addAttribute("itemLists", itemEntityLists);

		return "items/index";
	}
```

뷰 페이지에서는 타임리프 문법으로 itemLists 에서 item을 하나하나 꺼내 웹페이지에 렌더링합니다.
```java
<div layout:fragment="content">
	<div class="container">
		<div class="d-flex justify-content-start mb-4">
			<a href="/items/new" class="btn btn-primary">등록</a>
		</div>
		<div th:if="${itemLists.isEmpty()}">
			<p>등록된 작품이 없습니다.</p>
		</div>
		<div th:if="${not itemLists.isEmpty()}" class="row">
			<div th:each="item : ${itemLists}" class="col-md-3 mb-4">
				<a th:href="@{ '/items/' + ${item.id} }" class="text-decoration-none">
					<div class="card" style="width: 100%;">
						<img th:src="@{'/image/' + ${item.imageFileName}}"
							class="card-img-top" alt="...">
						<div class="card-body">
							<p class="card-text" th:text="${item.title}"></p>
						</div>
					</div>
				</a>
			</div>
		</div>
	</div>
</div>
```

### 작품 등록
form 태그의 action 속성으로 작품 등록 요청을 받는 컨트롤러로 요청을 보냅니다.

```java
<div layout:fragment="content">
	<form class="container" action="/items/create" method="post" enctype="multipart/form-data">
		<div class="form-group mb-3">
			<input type="file" class="form-control-file" name="image">
		</div>
		<div class="form-group mb-3">
			<input type="text" class="form-control" name="title"
				placeholder="작품명">
		</div>
		<div class="form-group mb-3">
			<textarea class="form-control" rows="3" name="description"
				placeholder="작품설명"></textarea>
		</div>
		<button type="submit" class="btn btn-primary">등록</button>	
		<a href="/" class="btn btn-secondary">목록</a>			
	</form>
</div>
```

컨트롤러에서는 서비스의 create 메서드로 클라이언트로부터 받은 form 객체를 전달합니다.
```java
// 새 작품 생성
	@PostMapping("/items/create")
	public String create(ItemForm form) {
		System.out.println(form.toString());

		itemService.create(form);
		return "redirect:/"; // return "items/index"; 하면 에러남. DB 저장 후 새로운 요청을 리다이렉트로 보내야 갱신된 값이 반영 됨.
	}
```

서비스 에서는 이미지 파일을 처리 한 후 Form 데이터인 DTO를 엔티티로 변환 한 다음

JPA Repository를 통해 데이터베이스에 저장합니다.

이미지 파일은 정해진 디렉터리에 저장 후 데이터베이스에는 이미지 파일명만 저장하는 방식으로 데이터베이스 용량 부담을 줄였습니다.
```java
// 새 작품 생성 메서드
	public void create(ItemForm form) {
		// 이미지 파일 처리
		MultipartFile imageFile = form.getImage(); // 이미지 파일 추출
		String imageFileName = null;
		
		if(!imageFile.isEmpty()) { // 이미지 파일 유효성 검사
			try {
				System.out.println("imageFile 조건 진입");
				// 이미지 파일을 저장할 디렉터리 설정
				File uploadDirFile = new File(uploadDir); // uploadDir 경로에 해당하는 디렉터리를 제어하는 File 객체 생성
				if(!uploadDirFile.exists()) {
					uploadDirFile.mkdirs();
				}
				System.out.println("업로드 디렉터리: " + uploadDir);
				
				// 파일 저장
				String originalFileName = imageFile.getOriginalFilename(); // 원래 파일명 추출
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 마지막 . 이후 문자열 자름 즉 확장자 .jpg
				String uniqueFileName = System.currentTimeMillis() + fileExtension; // 현재 시간을 밀리초 단위로 가져와서 확장자와 합친 문자열 생성
				File destinationFile = new File(uploadDirFile, uniqueFileName); // 이미지 파일을 저장할 경로 생성
				imageFile.transferTo(destinationFile); // 실제 저장
				imageFileName = uniqueFileName; 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 1. DTO를 엔티티로 변환
		Item itemEntity = form.toEntity(imageFileName);
		System.out.println(itemEntity.toString());
		
		// 2. 리파지터리로 엔티티를 DB에 저장
		Item saved = itemRepository.save(itemEntity);
		System.out.println(saved.toString());
	}
```

### 작품별 댓글 등록
댓글 생성 기능은 작품 생성과는 달리 비동기적으로 생성하기 위해, 자바스크립트의 fetch 메서드로 api 요청을 보냅니다.

```javascript
// 댓글 생성 처리
{
	// 댓글 생성 버튼 변수화
	const commentCreateBtn = document.querySelector("#comment-create-btn");
	
	// 댓글 클릭 이벤트 감지
	commentCreateBtn.addEventListener("click", function() {
		// 새 댓글 객체 생성
		const comment = {
			nickname : document.querySelector("#new-comment-nickname").value,
			star : document.querySelector("#new-comment-star").value,
			body : document.querySelector("#new-comment-body").value,
			itemId : document.querySelector("#new-comment-item-id").value,
		}
		console.log(comment); // 댓글 객체 출력
		
		// fetch - 비동기 통신을 위한 API
		const url = "/api/items/" + comment.itemId + "/comments";
		fetch(url, {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(comment)
		}).then(response => {
			// HTTP 응답 코드에 따른 메시지 출력
			/* const msg = (response.ok) ? "댓글이 등록되었습니다!" : "댓글 등록 실패!";
			alert(msg); */
			
			// 페이지 새로고침
			window.location.reload();
		});
	});		
}
```

fetch 메서드로 보내진 요청은 api 컨트롤러가 받아 서비스로 작품id와 폼 데이터를 전달합니다.

```javascript
// 댓글 생성(fetch 통해서 api 경유)
	@PostMapping("/api/items/{itemId}/comments")
	public ResponseEntity<CommentDto> create(@PathVariable("itemId") Long itemId, @RequestBody CommentDto dto) {
		CommentDto createdDto = commentService.create(itemId, dto);
		return ResponseEntity.status(HttpStatus.OK).body(createdDto);
	}
```

DB 수정이 실패할 경우를 대비해 @Transactional 어노테이션을 붙여 실패시 롤백되도록 했습니다.

댓글 생성 서비스에서는 전달 받은 작품id로 JPA Repository를 통해 데이터베이스에서 해당 작품 객체를 찾고,

찾은 작품 객체와 보내어 진 댓글 form을 이용해 댓글 엔티티를 생성 한 후 CommentRepository를 통해 데이터베이스에 댓글을 저장합니다.

```javascript
// 댓글 생성
	@Transactional
	public CommentDto create(Long itemId, CommentDto dto) {
		// 게시글 조회 및 예외 발생
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

		// 댓글 엔티티 생성
		Comment comment = Comment.createComment(dto, item);

		// 댓글 엔티티를 DB에 저장
		Comment created = commentRepository.save(comment);

		// DTO로 변환해 반환
		return CommentDto.createCommentDto(created);
	}
```

이 프로젝트의 거의 모든 동작은 위와 같은 과정들을 통해 이루어집니다.
