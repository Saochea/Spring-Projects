package com.bbc.restcrudoperation.controller;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbc.restcrudoperation.model.Product;
import com.bbc.restcrudoperation.service.ApiResponse;
import com.bbc.restcrudoperation.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
	
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping
//    public List<Product> getAllProducts() {
//        return productService.getAllProducts();
//    }
    
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
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
    	
    	if(product.getName().trim()==null || product.getName().trim()=="") {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("required", "Product name is require"));
    	}
    	
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
    
    // update product 
    @PutMapping("/{id}")
    public  ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
    	
    	if(product.getName()==null || product.getName()=="") {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("required","product name require"));
    	}
    	
    	try {
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
    
    // end point to query data by name or id
    @GetMapping("/search")
    public ResponseEntity<?> searchProductsByNameOrId(@RequestParam String query) {
        List<Product> products = productService.searchProductsByNameOrId(query);
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("FAILED", "Product name/id not found."));
        }
    }
    
    // pagination
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> products = productService.getAllProducts(page, size);
        return ResponseEntity.ok(products);
    }
    
}
