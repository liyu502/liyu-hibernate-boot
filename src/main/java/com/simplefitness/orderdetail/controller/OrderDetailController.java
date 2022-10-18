package com.simplefitness.orderdetail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simplefitness.orderdetail.service.impl.OrderDetailServiceImpl;
import com.simplefitness.orderdetail.service.intf.OrderDetailServiceIntf;
import com.simplefitness.orderdetail.vo.OrderDetail;

@RestController
@RequestMapping("/orderDetail")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderDetailController {
	@Autowired
	private OrderDetailServiceIntf detailService;
	
	@GetMapping("/{orderId}")
	public ResponseEntity<List<OrderDetail>> selectByOrderId(@PathVariable Integer orderId) {	
		List<OrderDetail> orderDetails = detailService.selectByOrderId(orderId);
		return ResponseEntity.status(HttpStatus.OK).body(orderDetails);
	}
}
