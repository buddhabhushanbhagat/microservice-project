package com.remitter.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CombinedData2 {

	private int customerId;
	private String name;
	private String email;
	private String address;
	private String password;
	private int maxLimit;

	private long accountNumber;
	private double accountBalance;
	private int accountStatus;

	private Date insertDate;
	private Date updateDate;

}
