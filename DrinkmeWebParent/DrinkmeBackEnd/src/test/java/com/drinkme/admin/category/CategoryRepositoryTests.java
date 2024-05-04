package com.drinkme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.drinkme.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {
	
	@Autowired
	private CategoryRepository repo;
	
	@Test
	public void testCreateRootCategory() {
		Category category = new Category("Analcolici");
		Category savedCategory = repo.save(category);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testDeleteCategory() {
		Integer categoryId = 30;
		repo.deleteById(categoryId);
	}
	
	
	@Test
	public void testGetCategoryById() {
		Category categoria = repo.findById(1).get();
		System.out.println(categoria);
		assertThat(categoria).isNotNull();
	}
	
	
	
	@Test
	public void testListAllCategories() {
		Iterable<Category> listCategories = repo.findAll();
		listCategories.forEach(category -> System.out.println(category));
	}
	
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	

	@Test
	public void testEnableCategory() {
		Integer id = 1;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testDisableCategory() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}
		
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 1;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Category> page = repo.findAll(pageable);
		
		List<Category> listCategories = page.getContent();
		listCategories.forEach(category -> System.out.println(category));
		
		assertThat(listCategories.size()).isEqualTo(pageSize);
	}
	
	
	
	@Test
	public void testSearchCategory() {
		String keyword = "Spumante";
		
		int pageNumber = 0;
		int pageSize = 20;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Category> page = repo.findAll(keyword, pageable);
		
		List<Category> listCategories = page.getContent();
		listCategories.forEach(category -> System.out.println(category));
		
		//assertThat(listCategories.size()).isGreaterThan(0); 
	}
	
	@Test
	public void testFindByName() {
		String name = "Vodka";
		Category category = repo.getCategoryByName(name);
		
		//assertThat(category).isNotNull();
		assertThat(category.getName()).isEqualTo(name);
	}
	
	@Test
	public void testFindByAlias() {
		String name = "Vodka";
		Category category = repo.findByAlias(name);
		
		//assertThat(category).isNotNull();
		assertThat(category.getName()).isEqualTo(name);
	}
	
	@Test
	public void testFindAll() {
		Iterable<Category> categories = repo.findAll();
		categories.forEach(System.out::println);
		
		//assertThat(categories).isNotEmpty();
	}
}
