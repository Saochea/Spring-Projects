package com.bbc.restcrudoperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbc.restcrudoperation.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can add custom query methods here if needed
	Product findByName(String name);
	//Product findByNameAndIdNot(String name,Long id);
}
