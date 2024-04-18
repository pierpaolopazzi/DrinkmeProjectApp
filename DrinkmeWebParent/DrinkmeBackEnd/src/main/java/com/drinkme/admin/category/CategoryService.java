package com.drinkme.admin.category;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.drinkme.common.entity.Category;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {
	public static final int CATEGORY_PER_PAGE = 10;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	public List<Category> listAll() {
		return (List<Category>) categoryRepo.findAll();
	}
	
	
	public Page<Category> listByPage(int pageNum, String keyword) {
		Pageable pageable = PageRequest.of(pageNum - 1, CATEGORY_PER_PAGE);
		
		if(keyword != null) {
			return categoryRepo.findAll(keyword, pageable);
		}
		
		return categoryRepo.findAll(pageable);
	}
	
	
	public void save(Category category) {
		// vero se esiste categoria con id
		boolean isUpdatingCategory = (category.getId() !=  null);
		
		categoryRepo.save(category);
		
		// verifico che sia una modifica
		// piuttosto che una nuova categoria
		if(isUpdatingCategory) {
			
			// recupero cateogria esistente
			Category existingCategory = categoryRepo.findById(category.getId()).get();
			
		}
		categoryRepo.save(category);
	}
	
	public boolean isCategoryUnique(Integer id, String name) {
		Category categoryByName = categoryRepo.getCategoryByName(name);
		
		if(categoryByName == null) return true;
		
		boolean isCreatingNew = (id==null);
		
		if(isCreatingNew) {
			if(categoryByName != null) return false;
		} else {
			if(categoryByName.getId() != id) {
				return false;
			}
		}
		
		return true;
	}
	
	public Category get(Integer id) throws CategoryNotFoundException {
		try {
			return categoryRepo.findById(id).get();
		} catch(NoSuchElementException ex) {
			throw new CategoryNotFoundException("Impossibile trovare categoria con id: " + id);
		}
	}
	
	public void delete(Integer id) throws CategoryNotFoundException {
		Long countById = categoryRepo.countById(id);
		if(countById == null || countById == 0) {
			throw new CategoryNotFoundException("Impossibile trovare la categoria con ID: " + id);
		}
		
		categoryRepo.deleteById(id);
	}
	
	
	public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
		categoryRepo.updateEnabledStatus(id, enabled);
	}
}
