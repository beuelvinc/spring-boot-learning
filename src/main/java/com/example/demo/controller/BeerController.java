package com.example.demo.controller;


import com.example.demo.model.Beer;
import com.example.demo.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;


@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

    public Beer getBeerById(UUID id){
        log.error("sfdf");
        return beerService.getBeersByID(id);
    }

}
