package com.lee.reviewmon.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lee.reviewmon.dto.ItemForm;
import com.lee.reviewmon.entity.Item;
import com.lee.reviewmon.repository.ItemRepository;

@Service
public class ItemService {
	@Autowired
	ItemRepository itemRepository;
	
	@Value("${file.upload-dir}")
	private String uploadDir;

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
}
