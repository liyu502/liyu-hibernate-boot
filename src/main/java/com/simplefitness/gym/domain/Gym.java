package com.simplefitness.gym.domain;

import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name="gym")
public class Gym extends Common {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gym_id")
	private Integer gymId;
	@Column(name = "gym_name")
	private String gymName;
	private String address;
	private String phone;
	@Column(name = "open_date")
	private Date openDate;
	@Column(name = "open_time")
	private LocalTime openTime;
	@Column(name = "close_time")
	private LocalTime closeTime;
	@Column(name = "max_p")
	private Integer maxPeople;
	private String intro;

}
