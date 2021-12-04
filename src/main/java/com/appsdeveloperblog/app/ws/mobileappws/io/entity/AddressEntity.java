package com.appsdeveloperblog.app.ws.mobileappws.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity(name = "addresses")
@Data
public class AddressEntity implements Serializable {

	private static final long serialVersionUID = 6757963427414876335L;

	@Id
	@GeneratedValue
	private long id;

	@Column(length = 30, nullable = false)
	private String addressId;
	@Column(length = 50, nullable = false)
	private String city;
	@Column(length = 50, nullable = false)
	private String country;
	@Column(length = 100, nullable = false)
	private String streetName;
	@Column(length = 10, nullable = false)
	private String postalCode;
	@Column(length = 10, nullable = false)
	private String types;
	@ManyToOne
	@JoinColumn(name = "users_id")
	private UserEntity userDetails;

}
