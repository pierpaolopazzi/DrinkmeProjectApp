package com.drinkme.admin.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRestController {
	
	@Autowired
	private CategoryService service;
	
	@PostMapping("/categorie/check_name")
	public String checkDuplicateName(@Param("id") Integer id, @Param("name") String name, @Param("alias") String alias) {
		return service.isCategoryUnique(id, name, alias);
	}
	
}