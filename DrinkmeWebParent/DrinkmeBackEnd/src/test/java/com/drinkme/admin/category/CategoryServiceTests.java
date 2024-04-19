package com.drinkme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.drinkme.common.entity.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

	@MockBean
	private CategoryRepository repo;
	
	@InjectMocks
	private CategoryService service;
	
	@Test
	public void testCheckUniqueInNewModeReturnDuplicateName() {
		Integer id = null;
		String name = "Vodka";
		String alias = "abc"; 
		
		Category category = new Category(id, name, alias);
		
		Mockito.when(repo.getCategoryByName(name)).thenReturn(category);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);
		
		String result = service.isCategoryUnique(id, name, alias);
		
		assertThat(result).isEqualTo("Duplicate Name");
	}
	
	@Test
	public void testCheckUniqueInNewModeReturnDuplicateAlias() {
		Integer id = null;
		String name = "Name";
		String alias = "vodka"; 
		
		Category category = new Category(id, name, alias);
		
		Mockito.when(repo.getCategoryByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(category);
		
		String result = service.isCategoryUnique(id, name, alias);
		
		assertThat(result).isEqualTo("Duplicate alias");
	}
	
	@Test
	public void testCheckUniqueInNewModeReturnDuplicateOK() {
		Integer id = null;
		String name = "Name";
		String alias = "vodka"; 
		
		Category category = new Category(id, name, alias);
		
		Mockito.when(repo.getCategoryByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);
		
		String result = service.isCategoryUnique(id, name, alias);
		
		assertThat(result).isEqualTo("OK");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnDuplicateName() {
		Integer id = 1;
		String name = "Name";
		String alias = "vodka"; 
		
		Category category = new Category(2, name, alias);
		
		Mockito.when(repo.getCategoryByName(name)).thenReturn(category);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);
		
		String result = service.isCategoryUnique(id, name, alias);
		
		assertThat(result).isEqualTo("Duplicate Name");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnDuplicateAlias() {
		Integer id = 1;
		String name = "Name";
		String alias = "vodka"; 
		
		Category category = new Category(2, name, alias);
		
		Mockito.when(repo.getCategoryByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(category);
		
		String result = service.isCategoryUnique(id, name, alias);
		
		assertThat(result).isEqualTo("Duplicate Alias");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnDuplicateOK() {
		Integer id = 1;
		String name = "Name";
		String alias = "vodka"; 
		
		Category category = new Category(id, name, alias);
		
		Mockito.when(repo.getCategoryByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);
		
		String result = service.isCategoryUnique(id, name, alias);
		
		assertThat(result).isEqualTo("OK");
	}
}
