package com.drinkme.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drinkme.common.entity.Category;

@Service
public class CategoryService {
	
	@Autowired private CategoryRepository repo;
	
	public List<Category> listCategories() {
		
		List<Category> listEnabledCategories = repo.findAllEnabled();

		return listEnabledCategories;
	}
	
	public Category getCategory(String alias) {
		return repo.findByAliasEnabled(alias);
	}
}
