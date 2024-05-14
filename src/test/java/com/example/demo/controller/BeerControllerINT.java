package com.example.demo.controller;

import com.example.demo.entities.Beer;
import com.example.demo.mappers.BeerMapper;
import com.example.demo.model.BeerDTO;
import com.example.demo.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerINT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerMapper beerMapper;

    @Test
    void testDeleteNotFoundBeer(){
        assertThrows(NotFoundException.class,()->{
            beerController.deleteById(UUID.randomUUID());
        });
    }
    @Test
    void testUpdateNotFoundBeer(){
        assertThrows(NotFoundException.class,()->{
            beerController.updateById(UUID.randomUUID(),
                    BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void saveNewBeerTest(){
        BeerDTO beerDTO = BeerDTO.builder().beerName("Test1").build();

        var responseEntity  = beerController.handlePost(beerDTO);
        assertEquals(responseEntity.getStatusCode().value(),201);
        assertNotNull(responseEntity.getHeaders().getLocation());

        var splittedPath =  responseEntity.getHeaders().getLocation().getPath().split("/");
        var beerId = splittedPath[splittedPath.length-1];
        var beer = beerRepository.findById(UUID.fromString(beerId));
        assertNotNull(beer);
    }

    @Rollback
    @Transactional
    @Test
    void  testUpdateBeer(){
        var beer = beerRepository.findAll().get(0);
        var beerDto =  beerMapper.beerToBeerDto(beer);
        beerDto.setId(null);
        beerDto.setVersion(null);
        beerDto.setBeerName("Updated");
        var responseEntity = beerController.updateById(beer.getId(),beerDto);
        assertEquals(responseEntity.getStatusCode().value(),204);
        assertEquals(beerRepository.findById(beer.getId()).get().getBeerName(),"Updated");
    }
    @Test
    void testGetByIdNotFound() {

        assertThrows(NotFoundException.class,()->{
            beerController.getBeerById(UUID.randomUUID());
        });
    }
    @Test
    void testGetById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerController.getBeerById(beer.getId());

        assertNotNull(beerDTO);
    }
    @Test
    void testListBeers(){
        List<BeerDTO> beerDTOS= beerController.listBeers();

        assertEquals(beerDTOS.size(),3);

    }

    @Rollback
    @Transactional
    @Test
    void testListBeersEmpty(){
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOS= beerController.listBeers();
        assertEquals(beerDTOS.size(),0);
    }


    @Rollback
    @Transactional
    @Test
    void testDeleteBeer(){
        var beer = beerRepository.findAll().get(0);
        var responseEntity = beerController.deleteById(beer.getId());
        assertEquals(responseEntity.getStatusCode().value(),204);

        var foundBeer = beerRepository.findById(beer.getId());
        assertEquals(foundBeer, Optional.empty());
    }
}