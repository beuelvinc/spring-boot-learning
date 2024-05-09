package com.example.demo.controller;

import com.example.demo.model.Beer;
import com.example.demo.service.BeerService;
import com.example.demo.service.BeerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;
@WebMvcTest(BeerController.class)
class BeerControllerTest  {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceimpl = new BeerServiceImpl();
    @Test
    void getBeerById() throws Exception {
        Beer testBeer = beerServiceimpl.listBeers().get(0);
        given(beerService.getBeerById(any(UUID.class))).willReturn(testBeer);
        mockMvc.perform(get("/api/v1/beer/"+testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName",is(testBeer.getBeerName())));
    }
    @Test
    void testListBeer() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceimpl.listBeers());
        mockMvc.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));

    }

    @Test
    void testCreateNewBeer() throws Exception {

        Beer beer = beerServiceimpl.listBeers().get(0);
        System.out.println(objectMapper.writeValueAsString(beer));
    }
}