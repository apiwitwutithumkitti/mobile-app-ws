package com.appsdeveloperblog.app.ws.mobileappws.service;

import java.util.List;

import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.CustomerDto;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customer);
	CustomerDto getCustomer(String id);
	List<CustomerDto> getCustomer(int page, int limit);
	
}
