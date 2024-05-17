package com.drinkme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
	
    @Test
    public void testListAll() {
        // Mocking data
        List<Category> categories = Arrays.asList(
            new Category(1, "Vodka", "Vodka"),
            new Category(2, "Gin", "Gin")
        );
        
        Mockito.when(repo.findAll()).thenReturn(categories);

        // Calling method to test
        List<Category> result = service.listAll();

        // Assertions
        assertThat(result).isEqualTo(categories);
    }
    
    @Test
    public void testListByPage() {
 
        Page<Category> page = new PageImpl<>(Arrays.asList(
            new Category(1, "Category 1", "cat1"),
            new Category(2, "Category 2", "cat2")
        ));
        Mockito.when(repo.findAll(Mockito.any(Pageable.class))).thenReturn(page);


        Page<Category> result = service.listByPage(1, null);

        
        assertThat(result).isEqualTo(page);
    }
    

    @Test
    public void testSaveNewCategory() {
        Category newCategory = new Category("New Category", "new");

        service.save(newCategory);

        Mockito.verify(repo, times(1)).save(newCategory);
    }
    
    @Test
    public void testGetCategoryExists() throws CategoryNotFoundException {
        Integer categoryId = 1;
        
        Category existingCategory = new Category(categoryId, "Existing Category", "existing");
        
        Mockito.when(repo.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        

        Category result = service.get(categoryId);
        
        assertThat(result).isEqualTo(existingCategory);
    }
    
    
    @Test
    public void testGetCategoryNotFound() {
        Integer categoryId = 100;
        
        Mockito.when(repo.findById(categoryId)).thenReturn(Optional.empty());
        
        // solleva l'eccezione
        assertThrows(CategoryNotFoundException.class, () -> service.get(categoryId));
    }
    
    
    @Test
    public void testDeleteCategoryIdIsNull() {
        // Verifica che venga lanciata l'eccezione NullPointerException se l'ID è null
        assertThrows(CategoryNotFoundException.class, () -> {
            service.delete(null);
        });

        // Verifica che il metodo deleteById non sia mai chiamato
        Mockito.verify(repo, times(0)).deleteById(null);
    }
    
    @Test
    public void testDeleteCategoryDoesNotExist() {
        Long countById = 0L;
        Integer categoryId = 1;
        Mockito.when(repo.countById(categoryId)).thenReturn(countById);

        // Verifica che venga lanciata l'eccezione CategoryNotFoundException
        assertThrows(CategoryNotFoundException.class, () -> {
            service.delete(categoryId);
        });

        // Verifica che il metodo deleteById non sia mai chiamato
        Mockito.verify(repo, times(0)).deleteById(categoryId);
    }
    
    @Test
    public void testDeleteCategoryExists() throws CategoryNotFoundException {
        // Mock del conteggio per un ID esistente
        Long countById = 1L;
        Integer categoryId = 1;
        Mockito.when(repo.countById(categoryId)).thenReturn(countById);

        // Chiamata al metodo delete
        service.delete(categoryId);

        // Verifica che il metodo deleteById sia stato chiamato una volta
        Mockito.verify(repo, times(1)).deleteById(categoryId);
    }
    
    
    @Test
    public void testUpdateCategoryEnabledStatusQueryAnnotation() {
        // Given
        int categoryId = 3;
        boolean enabled = true;

        // When
        service.updateCategoryEnabledStatus(categoryId, enabled);

        // Then
        Mockito.verify(repo, times(1)).updateEnabledStatus(categoryId, enabled);
    }
}
