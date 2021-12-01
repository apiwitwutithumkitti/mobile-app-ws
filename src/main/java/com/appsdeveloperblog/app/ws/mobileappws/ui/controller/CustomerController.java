package com.appsdeveloperblog.app.ws.mobileappws.ui.controller;

import com.appsdeveloperblog.app.ws.mobileappws.service.impl.CustomerServiceImpl;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.CustomerDto;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.request.CustomerDetailsRequsetModel;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.response.CustomerRest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    

    @Autowired 
    CustomerServiceImpl customerServiceImpl;

    @PostMapping
    public CustomerRest createUser(@RequestBody CustomerDetailsRequsetModel customerDetail) {
        CustomerRest returnValue = new CustomerRest();

        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customerDetail, customerDto);

        CustomerDto createCustomer = customerServiceImpl.createCustomer(customerDto);
        BeanUtils.copyProperties(createCustomer, returnValue);

        return returnValue;
    }

}
