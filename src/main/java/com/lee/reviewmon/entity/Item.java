package com.lee.reviewmon.entity;

import jakarta.persistence.*;

@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String title;
	@Column
	private String description;
	
	
	public Item() {
	}

	public Item(String title, String description) {
		this.title = title;
		this.description = description;
	}
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", title=" + title + ", description=" + description + "]";
	}	
	
}
