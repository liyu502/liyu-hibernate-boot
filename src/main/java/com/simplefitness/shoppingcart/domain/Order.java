package com.simplefitness.shoppingcart.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simplefitness.common.Common;

import ecpay.payment.integration.domain.AioCheckOutALL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
@Component
public class Order extends Common {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Integer orderId;
	@Column(name = "mem_id")
	private Integer memId;
	@Column(name = "gym_id")
	private Integer gymId;
	private Integer amount;
	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "order_date")
	private LocalDateTime orderDate;     
	private Integer status;
	@Transient
	private String gymName;
	@Transient
	private String memName;
	@Transient
	private String memEmail;
	@Transient
	private List<Count> orderList;
	@Transient
	private Long count;
	@Transient
	private Integer pageNo;
	@Transient
	private Integer totalPage;
	@Transient
	private AioCheckOutALL aio;
	@Transient
	private String payfor;
	
}
