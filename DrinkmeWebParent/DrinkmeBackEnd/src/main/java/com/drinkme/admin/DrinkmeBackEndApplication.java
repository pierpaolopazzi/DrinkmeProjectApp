package com.drinkme.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.drinkme.common.entity", "com.drinkme.admin.user"})
public class DrinkmeBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrinkmeBackEndApplication.class, args);
	}

}
