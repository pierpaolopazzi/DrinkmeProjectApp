package com.drinkme.admin.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.drinkme.common.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer>, ListPagingAndSortingRepository<Category, Integer> {
	
	

}
