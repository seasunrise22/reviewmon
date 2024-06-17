package com.lee.reviewmon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lee.reviewmon.dto.ItemForm;
import com.lee.reviewmon.entity.Item;
import com.lee.reviewmon.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	ItemService itemService;

	// 전체 작품 조회
	@GetMapping("/")
	public String index(Model model) {	
		// 1. 서비스에서 모든 Item 데이터 가져오기
		List<Item> itemEntityLists = itemService.getAll();

		// 2. 가져온 Item 묶음을 모델에 등록하기
		model.addAttribute("itemLists", itemEntityLists);

		return "items/index";
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
	
	// 개별 작품 선택
	@GetMapping("/items/{id}")
	public String showItem(@PathVariable("id") Long id) {
		System.out.println("id = " + id);
		return "items/new";
	}
}
