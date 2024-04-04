package com.drinkme.admin.category;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.drinkme.admin.user.UserNotFoundException;
import com.drinkme.common.entity.Category;

@Service
public class CategoryService {
	public static final int CATEGORY_PER_PAGE = 10;
	
	@Autowired
	private CategoryRepository repo;
	
	public List<Category> listAll() {
		return (List<Category>) repo.findAll();
	}
	
	
	public Page<Category> listByPage(int pageNum, String keyword) {
		Pageable pageable = PageRequest.of(pageNum - 1, CATEGORY_PER_PAGE);
		
		if(keyword != null) {
			return repo.findAll(keyword, pageable);
		}
		
		return repo.findAll(pageable);
	}
	
	
	public void save(Category category) {
		
		// vero se esiste categoria con id
		boolean isUpdatingCategory = (category.getId() !=  null);
		
		repo.save(category);
	}
	
	public Category get(Integer id) throws UserNotFoundException {
		try {
			return repo.findById(id).get();
		} catch(NoSuchElementException ex) {
			throw new UserNotFoundException("Impossibile trovare categoria con id: " + id);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = repo.countById(id);
		if(countById == null || countById == 0) {
			throw new UserNotFoundException("Impossibile trovare utenti con ID: " + id);
		}
		
		repo.deleteById(id);
	}
	
	
	
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}
}
