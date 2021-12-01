package com.appsdeveloperblog.app.ws.mobileappws.service.impl;

import com.appsdeveloperblog.app.ws.mobileappws.io.entity.CustomerEntity;
import com.appsdeveloperblog.app.ws.mobileappws.io.repositories.CustomerRepository;
import com.appsdeveloperblog.app.ws.mobileappws.service.CustomerService;
import com.appsdeveloperblog.app.ws.mobileappws.shared.Utils;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.CustomerDto;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@Override
	public CustomerDto getCustomer(String id) {
		CustomerEntity customerEntity = customerRepository.findByCustomerId(id);
		
		if (customerEntity == null)
			throw new RuntimeException("Customer with id : " + id + " not found.");
		
		CustomerDto returnValue = new CustomerDto();
		BeanUtils.copyProperties(customerEntity, returnValue);
		
		return returnValue;
	}

	@Override
	public List<CustomerDto> getCustomer(int page, int limit) {
		
		if (page > 0) page -= 1;
		
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		
		
		Page<CustomerEntity> customerPage = customerRepository.findAll(pageableRequest);
		List<CustomerEntity> customers = customerPage.getContent();
		
		List<CustomerDto> returnValue = new ArrayList<>();
		
		for (CustomerEntity customerEntity : customers) {
			CustomerDto customerDto = new CustomerDto();
			BeanUtils.copyProperties(customerEntity, customerDto);
			returnValue.add(customerDto);
		}
		
		return returnValue;
	}

}
