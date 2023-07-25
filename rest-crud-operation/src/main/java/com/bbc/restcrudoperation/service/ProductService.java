package com.bbc.restcrudoperation.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bbc.restcrudoperation.model.Product;
import com.bbc.restcrudoperation.repository.ProductRepository;
 
@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    
    // find all product
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }
    
    public Product getProductByName(String name) {
    	return repository.findByName(name);
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
    	
    	 Product existingProduct = repository.findById(id).orElse(null);

         if (existingProduct != null) {
             Product productWithSameName = repository.findByName(updatedProduct.getName());

             if (productWithSameName != null && !productWithSameName.getId().equals(id)) {
                 throw new IllegalArgumentException("A product with the same name already exists.");
            	 //return null;
             }

             existingProduct.setName(updatedProduct.getName());
             existingProduct.setPrice(updatedProduct.getPrice());
             return repository.save(existingProduct);
         }

         //return null;
         throw new IllegalArgumentException("product not found");
    }
    
    // delete product by id
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
  
    //service query product by name or id 
    public List<Product> searchProductsByNameOrId(String query) {
        try {
            Long id = Long.parseLong(query);
            return repository.findByIdOrNameContainingIgnoreCase(id, query);
        } catch (NumberFormatException ex) {
            return repository.findByNameContainingIgnoreCase(query);
        }
    }
    
    // service for pagination
    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
    
}
 
