package com.simplefitness.shoppingcart.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import com.simplefitness.common.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "idv_prod")
@Component
public class IdvProduct extends Common{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idv_id")
	private Integer idvId;
	@Column(name="gym_id")
	private Integer gymId;
	@Column(name="prod_id")
	private Integer prodId;
	@Column(name="`status`")
	private Integer status;
	@Transient
	private Long count;
	@Transient
	private Integer inCart;
	
	public IdvProduct(Integer prodId,Long count){
		this.prodId = prodId;
		this.count = count;
	}
	
}