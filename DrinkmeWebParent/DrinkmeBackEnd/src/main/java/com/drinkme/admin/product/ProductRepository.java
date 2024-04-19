package com.drinkme.admin.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.drinkme.common.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>, ListPagingAndSortingRepository<Product, Integer> {

}
