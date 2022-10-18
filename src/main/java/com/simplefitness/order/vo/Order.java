package com.simplefitness.order.vo;

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
import com.simplefitness.common.pojo.Common;

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
	private static final long serialVersionUID = 1L;
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
	private List<CountVO> orderList;
	@Transient
	private Long count;
	@Transient
	private Integer pageNo;
	@Transient
	private Integer totalPage;
	
//	@JsonIgnore
//	@ManyToOne
//	@JoinColumn(name = "gym_id", nullable = false, insertable = false, updatable = false)
//	private Gym gym;
	
//	public Order(Integer orderId, Integer gymId, Integer amount, LocalDateTime orderDate, Integer status,
//			String gymName) {
//		super();
//		this.orderId = orderId;
//		this.gymId = gymId;
//		this.amount = amount;
//		this.orderDate = orderDate;
//		this.status = status;
//		this.gymName = gymName;
//	}
	
}
