package com.bbc.restcrudoperation.service;
import java.util.List;
import org.springframework.stereotype.Service;

import com.bbc.restcrudoperation.model.Product;
import com.bbc.restcrudoperation.repository.ProductRepository;

@Service
public class ProductService {
	
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

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
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            return repository.save(existingProduct);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
 
