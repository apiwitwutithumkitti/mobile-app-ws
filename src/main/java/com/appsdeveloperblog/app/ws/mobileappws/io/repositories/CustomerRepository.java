package com.appsdeveloperblog.app.ws.mobileappws.io.repositories;

import com.appsdeveloperblog.app.ws.mobileappws.io.entity.CustomerEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long>{
    CustomerEntity findByEmail(String email);
}
