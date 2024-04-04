package com.drinkme.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.drinkme.admin.user.UserNotFoundException;
import com.drinkme.common.entity.Category;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService service;
/*
	@GetMapping("/categorie")
	public String listAll(Model model) {
		List<Category> listCategories = service.listAll();
		model.addAttribute("listCategories", listCategories);
		
		return "categorie/categorie";
	}
*/
	@GetMapping("/categorie")
	public String listFirstPage(Model model) {
		return listByPage(1, model, null);
	}
	
	@GetMapping("/categorie/page/{pageNum}")
	public String listByPage(
			@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("keyword") String keyword) {
		Page<Category> page = service.listByPage(pageNum, keyword);
		List<Category> listCategories = page.getContent();
		
		long startCount = (pageNum - 1) * CategoryService.CATEGORY_PER_PAGE;
		long endCount = startCount + CategoryService.CATEGORY_PER_PAGE -1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount); 
		model.addAttribute("totalItems",  page.getTotalElements());
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("keyword", keyword); 
		
		return "categorie/categorie";
	}
	
	
	@GetMapping("/categoria/nuova_categoria")
	public String newUser(Model model) {
		Category categoria = new Category();
		categoria.setEnabled(true);
		
		model.addAttribute("categoria", categoria);
		model.addAttribute("pageTitle", "Crea nuova categoria");
		
		return "categorie/form_utente";
	}	
	
	
	@GetMapping("/categorie/edit/{id}")
	public String editCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try{
			
			
			Category categoria = service.get(id);
			
			model.addAttribute("categoria", categoria);
			model.addAttribute("pageTitle", "Modifica categoria (ID: "+id+")");
			
			return "form_categoria";
		} catch(UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/categorie";
		}
	}
	
	
	@PostMapping("/categorie/salva")
	public String saveCategory(Category category, RedirectAttributes redirectAttributes) {
		System.out.println(category);
		service.save(category);
		
		redirectAttributes.addFlashAttribute("message", "La categoria e' stata salvata con successo!");
		return "redirect:/categorie";
	}

	
	@GetMapping("/categorie/delete/{id}")
	public String deleteCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try{
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "La categoria ID: "+id+" é stata eliminata correttamente"); 
		} catch(UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/categorie";
	}
	
	@GetMapping("/categorie/{id}/enabled/{status}")
	public String updateCategoryEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateUserEnabledStatus(id, enabled);
		String status = enabled ? "abilitata" : "disabilitata";
		String message = "La categoria " + id + " é stata " + status;
		redirectAttributes.addFlashAttribute("message", message);
	
		return "redirect:/categorie";
	}
	
	
	
	
	
	
	

}
