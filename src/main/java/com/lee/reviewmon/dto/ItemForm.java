package com.lee.reviewmon.dto;

import org.springframework.web.multipart.MultipartFile;

import com.lee.reviewmon.entity.Item;

public class ItemForm {
	private String title;
	private String description;
	private MultipartFile image;
	private String imageFileName;
	
	public ItemForm(String title, String description, String imageFileName) {
		this.title = title;
		this.description = description;
		this.imageFileName = imageFileName;
	}

	
	public ItemForm() {
	}


	public ItemForm(String title, String description, MultipartFile image) {
		this.title = title;
		this.description = description;
		this.image = image;
	}

	public Item toEntity(String imageFileName) {
		return new Item(title, description, imageFileName);
	}

	

	@Override
	public String toString() {
		return "ItemForm [title=" + title + ", description=" + description + ", image=" + image + "]";
	}


	// getter setter
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public MultipartFile getImage() {
		return image;
	}


	public void setImage(MultipartFile image) {
		this.image = image;
	}


	public String getImageFileName() {
		return imageFileName;
	}


	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
}
