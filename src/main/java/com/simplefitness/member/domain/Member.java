package com.simplefitness.member.domain;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
@Table(name="member")
public class Member extends Common {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mem_id")
	private Integer memId;
	@Column(name = "mem_name")
	private String memName;
	@Column(insertable = false)
	private String nickname;
	private String username;
	@Column(name = "mem_password")
	private String memPassword;
	@Column(insertable = false)
	private String phone;
	private String email;
	@Column(name = "email_verify", insertable = false)
	private Integer emailVerify;
	@Column(insertable = false)
	private Integer gender;
	@Column(insertable = false)
	private Date birth;
	@CreatedDate
	@Column(name = "register_date", insertable = false)
	private Date registerDate;
	@Column(name = "start_date", insertable = false)
	private Date startDate;
	@Column(name = "expire_date", insertable = false)
	private Date expireDate;
	@LastModifiedDate
	@Column(name = "last_login", insertable = false)
	private LocalDateTime lastLogin;
	@Column(name = "mem_status", insertable = false)
	private Integer memStatus;
	@Column(insertable = false, columnDefinition = "longblob (Types#LONGVARBINARY)")
	private byte[] pic;
	@Column(name = "qr_code", insertable = false)
	private String qrCode;
	@Transient
	private String memPicBase64;
	@Transient
	private String memNewPassword;
	

}
