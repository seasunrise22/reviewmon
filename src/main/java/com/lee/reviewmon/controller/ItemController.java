package com.lee.reviewmon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {
	
	@GetMapping("/")
	public String showAllItem() {
		return "item/list";
	}

}
