package com.drinkme.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drinkme.common.entity.Product;

@Service
public class ProductService {
	public static final int PRODUCT_PER_PAGE = 100;
	
	@Autowired private ProductRepository repo;
	/*
	public Page<Product> listByCategory(int pageNum, Integer categoryId) {
		PageRequest pageable = PageRequest.of(pageNum, PRODUCT_PER_PAGE);
		return repo.listByCategory(categoryId, pageable);
		
	}
	*/
	
	public List<Product> listProducts() {
		
		List<Product> listEnabledProducts = repo.findAllEnabled();

		return listEnabledProducts;
	}
	
	public List<Product> listByCategory(Integer categoryId) {
		
		List<Product> listProductsByCategory = repo.listByCategory(categoryId);
		
		return listProductsByCategory;
	}
	

}
