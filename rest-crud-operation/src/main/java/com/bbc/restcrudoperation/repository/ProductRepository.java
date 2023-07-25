package com.bbc.restcrudoperation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bbc.restcrudoperation.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
     
	 Product findByName(String name);

	 @Query("SELECT p FROM Product p WHERE p.id = :id OR UPPER(p.name) LIKE UPPER(concat('%', :query, '%'))")
	 List<Product> findByIdOrNameContainingIgnoreCase(@Param("id") Long id, @Param("query") String query);
	 List<Product> findByNameContainingIgnoreCase(String query);
	    
}
