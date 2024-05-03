package com.drinkme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.drinkme.category.CategoryService;
import com.drinkme.common.entity.Category;
import com.drinkme.common.entity.Product;
import com.drinkme.product.ProductService;

@Controller
public class MainController {
	
	@Autowired private CategoryService categoryService;
	@Autowired private ProductService productService;
	
	
	@GetMapping("")
	public String viewHomePage(Model model) {
		List<Category> listCategories = categoryService.listCategories();
		List<Product> listProducts = productService.listProducts();
		
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("listProducts", listProducts);
	
		return "index";
	}
}
