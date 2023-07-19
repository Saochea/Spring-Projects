package com.bbc.restcrudoperation.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbc.restcrudoperation.model.Product;
import com.bbc.restcrudoperation.service.ApiResponse;
import com.bbc.restcrudoperation.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
    	
        Product product =  productService.getProductById(id);
        if(product!=null) {
        	return ResponseEntity.ok(product);
        }
        
        ApiResponse response = new ApiResponse("failed","Product not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestBody Product product) {
    	
    	//============ check duplicate product name ============
    	Product alreadyExist = productService.getProductByName(product.getName().trim());	
    	if(alreadyExist!=null) {
    		ApiResponse response = new ApiResponse("failed","Product name already exist.");
    		return ResponseEntity.status(HttpStatus.CREATED).body(response);
    	}
    	
    	//================ end =======================
    	
    	// create product
    	Product createdProduct = productService.createProduct(product);
    	
    	if(createdProduct!=null) {
    		ApiResponse response = new ApiResponse("success","Product created successfully.");
    		return ResponseEntity.status(HttpStatus.CREATED).body(response);
    	}
    	
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    // update product service
    @PutMapping("/{id}")
    public  ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
    	
    	try {
    		// check if product id exist
    		Product isExist = productService.getProductById(id);
    		if(isExist==null) {
    			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("failed","update failed,product not found!"));
    		}
    		//  =================
    		
    		 productService.updateProduct(id, product);
    		 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("success", "Product updated successfully."));
    		 
    	}catch (IllegalArgumentException e) {
    		return ResponseEntity.badRequest().body(new ApiResponse("failed", e.getMessage()));
		}
    	
    }
    
    // delete product 
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
    	
    	Product isDeleted = productService.getProductById(id);
    	if(isDeleted!=null) {
    		productService.deleteProduct(id);
    		return ResponseEntity.ok(new ApiResponse("success","Product deleted successfully."));
    	}
    	
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("failed","Product not found."));
    }
}
