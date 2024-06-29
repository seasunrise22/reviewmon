package com.lee.reviewmon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lee.reviewmon.dto.CommentDto;
import com.lee.reviewmon.dto.ItemForm;
import com.lee.reviewmon.entity.Item;
import com.lee.reviewmon.repository.ItemRepository;
import com.lee.reviewmon.service.CommentService;
import com.lee.reviewmon.service.ItemService;

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

	// 개별 작품 선택
	@GetMapping("/items/{id}")
	public String showItem(@PathVariable("id")Long id, Model model) {
		// id를 조회해 데이터 가져오기
		Item itemEntity = itemService.getOne(id); // 댓글 등록할 때 어떤 작품인지 전달하기 위해 & 작품 정보 출력하기 위해 클릭한 작품 엔티티 넘겨 줌
		List<CommentDto> commentDtos = commentService.comments(id);

		// 모델에 데이터 등록
		model.addAttribute("item", itemEntity); // 작품 정보
		model.addAttribute("commentDtos", commentDtos); // 댓글 리스트
		
		return "items/show";
	}

	// 작품 등록 페이지 호출
	@GetMapping("/items/new")
	public String newForm() {
		return "items/new";
	}

	// 새 작품 생성
	@PostMapping("/items/create")
	public String create(ItemForm form) {
		System.out.println(form.toString());

		itemService.create(form);
		return "redirect:/"; // return "items/index"; 하면 에러남. DB 저장 후 새로운 요청을 리다이렉트로 보내야 갱신된 값이 반영 됨.
	}
	
	// 상품 수정 페이지 호출
	@GetMapping("/items/{id}/edit")
	public String edit(@PathVariable("id")Long id, Model model) {
		// 서비스에 id값 넘겨서 DB에서 데이터 가져오기
		ItemForm itemDTO = itemService.edit(id);
		
		// 가져온 데이터 모델에 등록
		model.addAttribute("item", itemDTO);
		
		// 수정 페이지 반환
		return "items/edit";
	}
	
	// 상품 수정 로직
	@PostMapping("/items/update")
	public String update(ItemForm form) {
		// form 데이터 넘겨서 서비스에서 수정 작업 수행
		itemService.update(form);
		
		return "redirect:/";
	}
	
	// 작품 삭제 요청 처리
	@GetMapping("/items/{id}/delete")
	public String delete(@PathVariable("id") Long id) {
		System.out.println("id value is " + id);
		
//		return "items/index"; "items/index" 하면 에러 나는데, 컨트롤러를 거쳐서 model.addattribute로 데이터를 매핑하지 않고 그냥 index.html 파일을 렌더링해서 모델값이 없어서 에러나는 듯. 
		return "redirect:/";
	}
}
