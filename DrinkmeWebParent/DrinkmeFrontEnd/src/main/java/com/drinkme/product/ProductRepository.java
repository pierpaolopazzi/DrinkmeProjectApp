package com.drinkme.product;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.drinkme.common.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {
	
	@Query("SELECT p FROM Product p WHERE p.enabled = true AND p.inStock = true ORDER BY p.name ASC")
	public List<Product> findAllEnabled();

	
}
