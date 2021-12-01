package com.appsdeveloperblog.app.ws.mobileappws.ui.controller;

import com.appsdeveloperblog.app.ws.mobileappws.service.CustomerService;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.CustomerDto;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.request.CustomerDetailsRequsetModel;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.response.CustomerRest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    

    @Autowired 
    CustomerService customerService;

    @PostMapping
    public CustomerRest createUser(@RequestBody CustomerDetailsRequsetModel customerDetail) {
        CustomerRest returnValue = new CustomerRest();

        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customerDetail, customerDto);

        CustomerDto createCustomer = customerService.createCustomer(customerDto);
        BeanUtils.copyProperties(createCustomer, returnValue);

        return returnValue;
    }
    
    @GetMapping(path="/{id}")
    public CustomerRest getCustomer(@PathVariable String id) {
    	CustomerRest returnValue = new CustomerRest();
    	
    	CustomerDto customerDto = customerService.getCustomer(id);
    	
    	BeanUtils.copyProperties(customerDto, returnValue);
    	
    	return returnValue;
    }
    
    @GetMapping
    public List<CustomerRest> getCustomer(@RequestParam(value="page", defaultValue="0") int page, 
    		@RequestParam(value="limit", defaultValue="2") int limit) {
    	List<CustomerRest> returnValue = new ArrayList<>();
    	
    	List<CustomerDto> customers = customerService.getCustomer(page, limit);
    	
    	for (CustomerDto customerDto : customers) {
    		CustomerRest customerModel = new CustomerRest();
    		BeanUtils.copyProperties(customerDto, customerModel);
    		returnValue.add(customerModel);
    	}
    	
    	return returnValue;
    }

}
