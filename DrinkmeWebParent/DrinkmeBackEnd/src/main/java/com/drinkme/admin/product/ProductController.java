package com.drinkme.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.drinkme.admin.category.CategoryService;
import com.drinkme.common.entity.Category;
import com.drinkme.common.entity.Product;

@Controller
public class ProductController {

	@Autowired private ProductService productService;
	@Autowired private CategoryService categoryService;
/*
	@GetMapping("/prodotti")
	public String listFirstPage(Model model) {
		return listByPage(1, model, null);
	}
	
	
	@GetMapping("/prodotti/page/{pageNum}")
	public String listByPage(
			@PathVariable(name="pageNum") int pageNum, Model model,
			@Param("keyword") String keyword) {
		Page<Product> page = productService.listByPage(pageNum, keyword);
		List<Product> listProducts = page.getContent();
		
		long startCount = (pageNum - 1) * ProductService.PRODUCT_PER_PAGE;
		long endCount = startCount + ProductService.PRODUCT_PER_PAGE -1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount); 
		model.addAttribute("totalItems",  page.getTotalElements());
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("keyword", keyword); 
		
		return "prodotti/prodotti";
		
		
	}
	
*/
	
	@GetMapping("/prodotti")
	public String listAll(Model model) {
		List<Product> listProducts = productService.listAll();
		
		model.addAttribute("listProducts", listProducts);
		return "prodotti/prodotti";
	}
	
	@GetMapping("/prodotti/nuovo_prodotto")
	public String newProduct(Model model) {
		List<Category> listCategory = categoryService.listAll();
		
		Product product = new Product();
		product.setEnabled(true);
		product.setInStock(true);
		
		model.addAttribute("product", product);
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("pageTitle", "Crea nuovo prodotto");
		
		return "prodotti/form_prodotto";
	}
	

	@PostMapping("/prodotti/salva")
	public String saveProduct(Product product, RedirectAttributes ra) {
		productService.save(product);
		ra.addFlashAttribute("message", "Il Prodotto è stato salvato con successo.");
		

		return "redirect:/prodotti";
	}
	
	@GetMapping("/prodotti/{id}/enabled/{status}")
	public String updateProductEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		productService.updateProductEnabledStatus(id, enabled);
		String status = enabled ? "abilitato" : "disabilitato";
		String message = "Il prodotto " + id + " é stato " + status;
		redirectAttributes.addFlashAttribute("message", message);
	
		return "redirect:/prodotti";
	}

}
