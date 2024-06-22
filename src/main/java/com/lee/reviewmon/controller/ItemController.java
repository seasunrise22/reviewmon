package com.lee.reviewmon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lee.reviewmon.dto.CommentDto;
import com.lee.reviewmon.dto.ItemForm;
import com.lee.reviewmon.entity.Item;
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
	public String showItem(@PathVariable("id") Long id, Model model) {
		// id를 조회해 데이터 가져오기
		Item itemEntity = itemService.getOne(id); // 댓글 등록할 때 어떤 작품인지 전달하기 위해 작품 엔티티 넘겨 줌
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
}
