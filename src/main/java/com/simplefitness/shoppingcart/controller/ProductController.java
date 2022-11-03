package com.simplefitness.shoppingcart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplefitness.shoppingcart.domain.IdvProduct;
import com.simplefitness.shoppingcart.domain.Product;
import com.simplefitness.shoppingcart.service.impl.IdvProductServiceImpl;
import com.simplefitness.shoppingcart.service.impl.ProductServiceImpl;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {
	@Autowired
	private IdvProductServiceImpl idvProductService;
	@Autowired
	private ProductServiceImpl productService;

	@GetMapping("/{gymId}")
	public ResponseEntity<List<Product>> selectByGym(@PathVariable Integer gymId) {	
		List<IdvProduct> idvProducts = idvProductService.selectByGym(gymId);
        List<Product> products = new ArrayList<Product>();
        for (int i = 0; i < idvProducts.size(); i++) {
        	products.add(productService.selectById(idvProducts.get(i).getProdId()));
        }
        for (int i = 0; i < idvProducts.size(); i++) {
        	products.get(i).setCount(idvProducts.get(i).getCount());
        }
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}
	
	@GetMapping("/search/{name}/{gymId}")
	public ResponseEntity<List<Product>> searchByName(@PathVariable String name, @PathVariable Integer gymId, @RequestHeader(value = "prodIds")  List<Integer> prodIds) {
		List<Product> products = productService.findByName(name, prodIds);
		for (int i = 0; i < products.size(); i++) {
        	products.get(i).setCount(idvProductService.selectCount(gymId, prodIds.get(i))); 
        }
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}
	

}
