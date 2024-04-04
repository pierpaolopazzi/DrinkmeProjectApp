package com.drinkme.admin.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.drinkme.common.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer>, 
								ListPagingAndSortingRepository<Category, Integer> {
	
	
	public Long countById(Integer id);
	
	@Query("SELECT u FROM Category u WHERE CONCAT(u.id, ' ', u.name, ' ', u.alias) LIKE %?1%") 
	public Page<Category> findAll(String keyword, Pageable pagable);
	
	
	@Query("UPDATE Category u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
}
