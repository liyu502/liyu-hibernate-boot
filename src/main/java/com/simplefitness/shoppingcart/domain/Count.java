package com.simplefitness.shoppingcart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Count {
	
	private Integer inCart; 
	private Integer prodId;
	private String prodName;
}