package com.booking.hotelapi.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "BED_TYPES")
public class BedType implements Serializable{
	
	private static final long serialVersionUID = 5433713775530016813L;
	
	@Id
	@Column(name="BED_TYPE_ID", updatable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bedTypeId;
	
	@Column(name="NAME", updatable=true)
	@Length(max = 45, min = 0)
	@NotNull(message = "Name is required")
	private String name;
	
	@Column(name="SIZE", updatable=true, length=1)
	@NotNull(message = "Size is required")
	private Integer size;
	
	@Transient
	@JsonIgnore
	@OneToMany(mappedBy = "bedTypeObj")
	private List<Room> rooms;
}
