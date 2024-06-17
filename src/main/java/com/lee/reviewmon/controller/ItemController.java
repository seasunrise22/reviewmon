package com.lee.reviewmon.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.lee.reviewmon.dto.ItemForm;
import com.lee.reviewmon.entity.Item;
import com.lee.reviewmon.repository.ItemRepository;
import com.lee.reviewmon.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ItemService itemService;

	// 전체 작품 조회
	@GetMapping("/item/list")
	public String list(Model model) {
		// 1. DB에서 모든 Item 데이터 가져오기
		ArrayList<Item> itemEntityList = itemRepository.findAll();

		// 2. 가져온 Item 묶음을 모델에 등록하기
		model.addAttribute("itemList", itemEntityList);

		return "item/list";
	}

	// 작품 등록 페이지
	@GetMapping("/item/new")
	public String newForm() {
		return "item/new";
	}

	// 새 작품 생성
	@PostMapping("/item/create")
	public String create(ItemForm form) {
		System.out.println(form.toString());
		
		itemService.create(form);
		return "redirect:/item/list"; // return "item/list"; 하면 에러남. DB 저장 후 새로운 요청을 리다이렉트로 보내야 갱신된 값이 반영 됨.
	}
}
