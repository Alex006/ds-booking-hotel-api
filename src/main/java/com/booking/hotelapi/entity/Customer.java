package com.booking.hotelapi.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "CUSTOMERS")
public class Customer implements Serializable{
	
	private static final long serialVersionUID = 5433714774530016814L;
	
	@Id
	@Column(name="CUSTOMER_ID", updatable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;
	
	@Column(name="FIRST_NAME", updatable=true)
	@Length(max = 45, min = 0)
	@NotNull(message = "First name is required")
	private String firstName;

	@Column(name="LAST_NAME", updatable=true, length=2)
	@NotNull(message = "Last name is required")
	private String lastName;
	
	@Column(name="BIRTH_DATE", updatable=true, length=1)
	@NotNull(message = "Birth date is required")
	private LocalDate birthDate;
	
	@Column(name="EMAIL", updatable=true)
	@Length(max = 45, min = 0)
	@NotNull(message = "First name is required")
	private String email;
	
	@Column(name="PHONE_NUMBER", updatable=true)
	@Length(max = 45, min = 0)
	@NotNull(message = "First name is required")
	private String phoneNumber;
	
}
