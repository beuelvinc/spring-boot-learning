package com.example.demo.controller;

import com.example.demo.model.BeerDTO;
import com.example.demo.service.BeerService;
import com.example.demo.service.impl.BeerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;
@WebMvcTest(BeerController.class)
class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceimpl;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;
    @BeforeEach
    void setUp(){
        beerServiceimpl =  new BeerServiceImpl();
    }
    @Test
    void getBeerById() throws Exception {
        BeerDTO testBeer = beerServiceimpl.listBeers().get(0);
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.ofNullable(testBeer));
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

        BeerDTO beer = beerServiceimpl.listBeers().get(0);

        beer.setVersion(null);
        beer.setId(null);
        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceimpl.listBeers().get(1));

        mockMvc.perform(post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void updateBeer() throws Exception {
        BeerDTO beer  = beerServiceimpl.listBeers().get(0);

        given(beerService.updateBeerById(any(),any()))
                .willReturn(Optional.of(beer));

        mockMvc.perform(put("/api/v1/beer/"+beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        //verifies that the method was called with those parameters
        verify(beerService).updateBeerById(any(UUID.class),any(BeerDTO.class));

    }

    @Test
    void deleteBeer() throws Exception {

        BeerDTO beer = beerServiceimpl.listBeers().get(0);

        given(beerService.deleteById(any())).willReturn(true);
        mockMvc.perform(delete("/api/v1/beer/"+beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(beerService).deleteById(uuidArgumentCaptor.capture());

        assertEquals(beer.getId(),uuidArgumentCaptor.getValue());
    }


    @Test
    void patchBeer() throws Exception {

        BeerDTO beer = beerServiceimpl.listBeers().get(0);
        HashMap<String,String> beerMap = new HashMap<>();

        beerMap.put("beerName","Elvin");

        mockMvc.perform(patch("/api/v1/beer/"+beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());



        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(),beerArgumentCaptor.capture());

        assertEquals(beer.getId(),uuidArgumentCaptor.getValue());

        assertEquals(beerMap.get("beerName"),beerArgumentCaptor.getValue().getBeerName());

    }
}