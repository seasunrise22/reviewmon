package com.lee.reviewmon.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

	// 전체 작품 목록 반환
	public List<Item> getAll() {
		return itemRepository.findAll();
	}

	// 단일 작품 반환
	public Item getOne(Long id) {
		return itemRepository.findById(id).orElse(null);
	}

	// 새 작품 생성 메서드
	public void create(ItemForm form) {
		// 이미지 파일 처리
		MultipartFile imageFile = form.getImage(); // 이미지 파일 추출
		String imageFileName = null;

		if (!imageFile.isEmpty()) { // 이미지 파일 유효성 검사
			try {
				System.out.println("imageFile 조건 진입");
				// 이미지 파일을 저장할 디렉터리 설정
				File uploadDirFile = new File(uploadDir); // uploadDir 경로에 해당하는 디렉터리를 제어하는 File 객체 생성
				if (!uploadDirFile.exists()) {
					uploadDirFile.mkdirs();
				}
				System.out.println("업로드 디렉터리: " + uploadDir);

				// 파일 저장
				String originalFileName = imageFile.getOriginalFilename(); // 원래 파일명 추출
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 마지막 . 이후 문자열 자름
																										// 즉 확장자 .jpg
				String uniqueFileName = System.currentTimeMillis() + fileExtension; // 현재 시간을 밀리초 단위로 가져와서 확장자와 합친 문자열
																					// 생성
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

	// DTO -> 엔티티 변환 함수
	public Item convertToEntity(ItemForm itemForm) {
		return new Item(
				// Long id, String title, String description, String imageFileName
				itemForm.getId(), itemForm.getTitle(), itemForm.getDescription(), itemForm.getImageFileName());
	}

	// 엔티티 -> DTO 변환 함수
	public ItemForm convertToDTO(Item itemEntity) {
		return new ItemForm(
				// Long id, String title, String description, String imageFileName
				itemEntity.getId(), itemEntity.getTitle(), itemEntity.getDescription(), itemEntity.getImageFileName());
	}

	// 수정할 기존 게시글 전송용
	public ItemForm edit(Long id) {
		// id로 DB에서 ItemEntity 객체 찾아서 반환
		Item itemEntity = itemRepository.findById(id).orElse(null);

		// 엔티티를 DTO로 변환
		ItemForm itemDTO = convertToDTO(itemEntity);

		return itemDTO;
	}

	/*
	 * 기존 방식 // 수정 할 내용 받아서 저장하는 로직 public Item update(ItemForm form) { // DTO ->
	 * 엔티티 변환 Item item = convertToEntity(form);
	 * 
	 * // 수정할 기존 데이터 가져오기 Item target =
	 * itemRepository.findById(form.getId()).orElse(null);
	 * 
	 * if(target != null) // itemEntity에 id 값이 이미 존재하는 경우, 새로운 행이 생성되는 것이 아니라 해당 id에
	 * 해당하는 기존 엔티티의 데이터가 갱신 됨. 이러한 동작은 JPA의 기본적인 기능 itemRepository.save(item);
	 * 
	 * return item; }
	 */

	/* 개선 된 방식 */
	// 수정 할 내용 받아서 저장하는 로직
	public Item update(ItemForm form) {
		// 수정할 기존 데이터 가져오기
		Item target = itemRepository.findById(form.getId()).orElse(null);

		if (target != null) {
			// 새로운 이미지 파일이 있는 경우 처리
			MultipartFile imageFile = form.getImage();
			if(!imageFile.isEmpty()) {
				try {
					// 기존 이미지 파일 삭제
					if(target.getImageFileName() != null) {
						
					}
					
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
			
			// 기존 데이터의 정보를 새로운 데이터로 변경
			target.setTitle(form.getTitle());
			target.setDescription(form.getDescription());
			target.setImageFileName(form.getImageFileName());
			
			// 변경된 데이터를 DB에 저장
			// JPA의 save() 메서드는 다음과 같은 동작을 함.
			// 1. 엔티티의 식별자(id)가 null이면 새로운 엔티티를 생성하고 저장
			// 2. 엔티티의 식별자(id)가 존재하면 해당 엔티티의 데이터를 갱신합니다.
			// 따라서 target 엔티티의 데이터를 변경하고 itemRepository.save(target) 메서드를 호출하면 데이터베이스에 변경된 데이터가 저장되며, 갱신된 Item 객체가 반환 됨
			return itemRepository.save(target);
		}

		return null;
	}
}

/*
 * public Item update(ItemForm form) {
    // DTO -> 엔티티 변환
    Item item = convertToEntity(form);

    // 수정할 기존 데이터 가져오기
    Item target = itemRepository.findById(form.getId()).orElse(null);

    if (target != null) {
        // 새로운 이미지 파일이 있는 경우 처리
        MultipartFile imageFile = form.getImage();
        if (!imageFile.isEmpty()) {
            try {
                // 기존 이미지 파일 삭제
                if (target.getImageFileName() != null) {
                    File existingFile = new File(uploadDir, target.getImageFileName());
                    existingFile.delete();
                }

                // 새로운 이미지 파일 저장
                String originalFileName = imageFile.getOriginalFilename();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String uniqueFileName = System.currentTimeMillis() + fileExtension;
                File destinationFile = new File(uploadDir, uniqueFileName);
                imageFile.transferTo(destinationFile);
                item.setImageFileName(uniqueFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 새로운 이미지 파일이 없는 경우 기존 이미지 파일을 유지
            item.setImageFileName(target.getImageFileName());
        }

        // 데이터 업데이트
        return itemRepository.save(item);
    }

    return null;
}
*/
