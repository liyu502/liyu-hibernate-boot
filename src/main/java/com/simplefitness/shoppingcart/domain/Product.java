package com.simplefitness.shoppingcart.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="product")
@Component
public class Product extends Common{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prod_id")
	private Integer prodId;
	@Column(name = "prod_name")
	private String prodName;
	private Integer price;
	private String intro;
	@Lob
//	@Column(columnDefinition = "longblob (Types#LONGVARBINARY)")
	private byte[] pic;
	@Transient
	private long count;
	@Transient
	private List<Integer> prodIds;
	
}
