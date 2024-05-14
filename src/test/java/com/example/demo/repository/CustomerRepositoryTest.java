package com.example.demo.repository;

import com.example.demo.entities.Customer;
import com.example.demo.model.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;


    @Test
    void savedCustomer(){
        Customer savedCustomer = customerRepository.save(Customer.builder()
                        .name("test")
                .build());

        assertNotNull(savedCustomer.getId());
    }
}