package com.example.demo.repository;

import com.example.demo.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {


    @Autowired
    BeerRepository beerRepository;

    @Test
    void savedBeer(){
        Beer beer = beerRepository.save(Beer.builder().beerName("beer name").build());

        assertNotNull(beer);
        assertEquals(beer.getBeerName(),"beer name");
        assertNotNull(beer.getId());
    }
}