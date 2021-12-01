package com.appsdeveloperblog.app.ws.mobileappws.service.impl;

import com.appsdeveloperblog.app.ws.mobileappws.io.entity.CustomerEntity;
import com.appsdeveloperblog.app.ws.mobileappws.io.repositories.CustomerRepository;
import com.appsdeveloperblog.app.ws.mobileappws.service.CustomerService;
import com.appsdeveloperblog.app.ws.mobileappws.shared.Utils;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.CustomerDto;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public CustomerDto createCustomer(CustomerDto customer) {
        if (customerRepository.findByEmail(customer.getEmail()) != null ){
            throw new RuntimeException("customer already exists"); 
        }

        CustomerEntity entity = new CustomerEntity();
        BeanUtils.copyProperties(customer, entity);

        String publicCustomerId = utils.generateUserId(30);
        entity.setCustomerId(publicCustomerId);
        entity.setEncryptedPassword(bCryptPasswordEncoder.encode(customer.getPassword()));

        CustomerEntity storedCustomerDetails = customerRepository.save(entity);

        CustomerDto returnValue = new CustomerDto();
        BeanUtils.copyProperties(storedCustomerDetails, returnValue);

        return returnValue;

    }

}
