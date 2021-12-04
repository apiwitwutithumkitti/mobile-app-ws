package com.appsdeveloperblog.app.ws.mobileappws.ui.model.response;

import org.springframework.hateoas.RepresentationModel;

public class AddressesRest extends RepresentationModel<AddressesRest> {
	private String addressId;
	private String city;
	private String country;
	private String streetName;
	private String postalCode;
	private String types;
	
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	

}
