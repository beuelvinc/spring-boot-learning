package com.example.demo.service;

import com.example.demo.model.Beer;
import com.example.demo.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public Beer getBeersByID(UUID id) {
        log.debug("sdfsd");
        return Beer.builder().id(id).version(1).beerName("galax")
                .beerStyle(BeerStyle.ALE).upc("234")
                .price(new BigDecimal("12.09")).quantityOnHand(232)
                .createdDate(LocalDateTime.now()).updatedDate(LocalDateTime.now())
                .build();
    }
}
