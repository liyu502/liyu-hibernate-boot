package com.simplefitness.order.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplefitness.order.service.intf.OrderServiceIntf;
import com.simplefitness.order.vo.Order;
import com.simplefitness.order.vo.PageVo;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {
	@Autowired
	private OrderServiceIntf orderService;
	
	@GetMapping("/orders/{memId}")
	public ResponseEntity<List<Order>> selectByMem(@PathVariable Integer memId, @RequestHeader(value = "pageNo") Integer pageNo, @RequestHeader(value = "pageSize") Integer pageSize ) {
//		Integer memId = order.getMemId();
//		Integer pageNo = order.getPage().getPageNo();
//		Integer pageSize = order.getPage().getPageSize();
//		System.out.println(page.getPage().getPageNo());
		List<Order> orders = orderService.selectByMem(memId, pageNo, pageSize);
		return ResponseEntity.status(HttpStatus.OK).body(orders);
	}
	
	@PutMapping
	public ResponseEntity<String> cancelOrder(@RequestBody Order order) {	
		orderService.cancelOrder(order);
		return ResponseEntity.status(HttpStatus.OK).body("successfully");
	}
	
	@PostMapping
	public ResponseEntity<Order> addOrder(@RequestBody Order order) {	
		Order result = orderService.addOrder(order);
		return ResponseEntity.status(HttpStatus.OK).body(result); 
	}

}
