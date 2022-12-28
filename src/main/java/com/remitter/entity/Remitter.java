package com.remitter.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

//@AllArgsConstructor

//@Getter
//@Setter
//@EqualsAndHashCode
//@ToString
@Entity
@NoArgsConstructor
@Data
@Table(name = "remitter")
public class Remitter implements Serializable {
	public Remitter(String name2, String email2, String address2, String password2) {
		// TODO Auto-generated constructor stub
		this.name = name2;
		this.email = email2;
		this.address = address2;
		this.password = password2;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7692053907710265029L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int customerId;

	@NotNull(message = "name can not be empty")
	@Column(name = "name")
	private String name;

	@NotNull(message = "email can not be empty")
	@Column(name = "email")
	private String email;

	@NotNull(message = "address can not be empty")
	@Column(name = "address")
	private String address;

	@Column(name = "password")
	private String password;

	@Column(columnDefinition = "integer default 20", name = "max_limit")
	private int maxLimit;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false, name = "insert_date")
	private Date insertDate;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false, name = "update_date")
	private Date updateDate;

}
