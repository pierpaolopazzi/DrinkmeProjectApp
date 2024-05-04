package com.drinkme.product;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.drinkme.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTests {
	
	
	@Autowired private ProductRepository repo;
	
	@Test
	public void testListEnabledProducts() {
		List<Product> products = repo.findAllEnabled();
		products.forEach(product -> {
			System.out.println(product.getName() + " - Enabled -> (" + product.isEnabled() + ")" + " - In-Stock -> (" + product.isInStock() + ")");
		});
	}

}

