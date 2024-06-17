package com.lee.reviewmon.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lee.reviewmon.entity.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {

	@Override
	List<Item> findAll();	
}
