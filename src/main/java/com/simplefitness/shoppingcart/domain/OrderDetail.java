package com.simplefitness.shoppingcart.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name="order_detail")
@Component
public class OrderDetail extends Common{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detail_id")
	private Integer detailId;
	@Column(name = "order_id")
	private Integer orderId;
	@Column(name = "idv_id")
	private Integer idvId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "pickup_time", insertable = false)
	private Timestamp pickupTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "return_time", insertable = false)
	private Timestamp returnTime;
	private Integer status;
	@Transient
	private Integer gymId;
	@Transient
	private Integer prodId;
	@Transient
	private Integer inCart;
	@Transient
	private String prodName;
	
	public OrderDetail(Integer idvId, Timestamp pickupTime, Timestamp returnTime, Integer status, String prodName) {
		this.idvId = idvId;
		this.pickupTime =pickupTime;
		this.returnTime = returnTime;
		this.status = status;
		this.prodName = prodName;
	}
	
}
