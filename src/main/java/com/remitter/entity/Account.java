package com.remitter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name="account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable{
	public Account(long accountNumber2, double accountBalance2, int accountStatus2) {
		// TODO Auto-generated constructor stub
		this.accountNumber = accountNumber2;
		this.accountBalance=accountBalance2;
		this.accountStatus = accountStatus2;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6660548820343363086L;

	@Id
	@Column(name="account_number")
	private long accountNumber;
	
	@Column(name="account_balance")
	@NotNull(message = "Balance is null")
	@PositiveOrZero(message = "balance must be positive value")
	private double accountBalance;
	
	@Column(name="account_status")
	private int accountStatus;
	
//	@CreatedDate
//	@Column(name = "created_at", nullable = false, updatable = false)
	@Column(nullable = false, updatable = false,name="insert_date")
	@CreationTimestamp
	private Date insertDate; 
//	@LastModifiedDate
//	@Column(name = "updated_at")
	@Column(nullable = false, updatable = false,name="update_date")
	@UpdateTimestamp
	private Date updateDate; 
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Remitter remitter;
}
