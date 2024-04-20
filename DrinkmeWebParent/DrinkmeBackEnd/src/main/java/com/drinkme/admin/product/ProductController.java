package com.drinkme.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.drinkme.common.entity.Product;

@Controller
public class ProductController {

	@Autowired private ProductService productService;
	
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
	
	
}
