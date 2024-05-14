package com.example.demo.bootstrap;

import com.example.demo.repository.BeerRepository;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    CustomerRepository customerRepository;
    BootstrapData bootstrapData;
    @BeforeEach
    void setUp(){
        bootstrapData = new BootstrapData(beerRepository, customerRepository);
    }
    @Test
    void testRun() throws Exception {
        bootstrapData.run(null);
        assertEquals(beerRepository.count(),3);
        assertEquals(customerRepository.count(),3);

    }
}