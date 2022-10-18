package com.simplefitness.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simplefitness.idvproduct.service.impl.IdvProductServiceImpl;
import com.simplefitness.idvproduct.service.intf.IdvProductServiceIntf;
import com.simplefitness.idvproduct.vo.IdvProduct;
import com.simplefitness.product.service.impl.ProductServiceImpl;
import com.simplefitness.product.service.intf.ProductServiceIntf;
import com.simplefitness.product.vo.Product;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {
	@Autowired
	private IdvProductServiceIntf idvProductService;
	@Autowired
	private ProductServiceIntf productService;

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
	
	@GetMapping("/search/{name}")
	public ResponseEntity<List<Product>> searchByName(@PathVariable String name, @RequestHeader(value = "prodIds")  List<Integer> prodIds) {
		System.out.println(name);
		System.out.println(prodIds);
		List<Product> products = productService.findByName(name, prodIds);
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}
	

}
