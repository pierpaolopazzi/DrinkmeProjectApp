package com.drinkme.admin.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.drinkme.common.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>, ListPagingAndSortingRepository<Product, Integer> {


	public Product findByName(String name);
	
	@Query("UPDATE Product p SET p.enabled = ?2 WHERE p.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
	public Long countById(Integer id);
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1% "
			+ "OR p.shortDescription LIKE %?1% "
			+ "OR p.category.name LIKE %?1% ")
	public Page<Product> findAll(String keyword, Pageable pageable);
}
