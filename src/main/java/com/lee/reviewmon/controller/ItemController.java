package com.lee.reviewmon.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.lee.reviewmon.dto.ItemForm;
import com.lee.reviewmon.entity.Item;
import com.lee.reviewmon.repository.ItemRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ItemController {
	@Autowired
	ItemRepository itemRepository;
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	
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
	public String create(ItemForm form, HttpServletRequest request) {
		System.out.println(form.toString());
		
		// 이미지 파일 처리
		MultipartFile imageFile = form.getImage(); // 이미지 파일 추출
		String imageFileName = null;
		
		if(imageFile != null && !imageFile.isEmpty()) { // 이미지 파일 유효성 검사
			try {
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
		
		return "redirect:/item/list"; // return "item/list"; 하면 에러남. DB 저장 후 새로운 요청을 리다이렉트로 보내야 갱신된 값이 반영 됨. 
	}

}
