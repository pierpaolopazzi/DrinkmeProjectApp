package com.drinkme.admin.product;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.drinkme.common.entity.Product;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
	
	public static final int PRODUCTS_PER_PAGE = 8;
	
	@Autowired
	private ProductRepository repo;
	
	public List<Product> listAll() {
		return (List<Product>) repo.findAll();
	}
	
	public Page<Product> listByPage(int pageNum, String keyword){
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		
		if(keyword != null) {
			return repo.findAll(keyword, pageable);
		}
		
		return repo.findAll(pageable);
	}
	

	public Product save(Product product) {
			
		repo.save(product);
		
		if(product.getId() == null ) {
			product.setCreatedTime(new Date());
			
		}
		
		if(product.getAlias() == null || product.getAlias().isEmpty()) {
			String defaultAlias = product.getName().replaceAll(" ", "-");
			product.setAlias(defaultAlias);
		} else {
			product.setAlias(product.getAlias().replaceAll(" ", "-"));
		}
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		return repo.save(product);

	}
	
	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Product productByName = repo.findByName(name);
		
		if(isCreatingNew) {
			if(productByName != null) {
				return "Duplicate";
			}
		} else {
			if (productByName != null && productByName.getId() != id) {
				return "Duplicate";
			}
		}
		
		return "OK";
	}
	
	
	
	public void updateProductEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}
	
	
	
	public void delete(Integer id) throws ProductNotFoundException {
		Long countById = repo.countById(id);
		
		if(countById == null || countById == 0) {
			throw new ProductNotFoundException("Impossibile trovare il prodotto con ID " + id);
			
		}
		
		repo.deleteById(id);
	}
	
	
	public Product get(Integer id) throws ProductNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new ProductNotFoundException("Impossibile trovare il prodotto con ID " + id);
		}
		
	}
}
 