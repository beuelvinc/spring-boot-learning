package com.example.demo.service.jpaImpl;

import com.example.demo.entities.Beer;
import com.example.demo.mappers.BeerMapper;
import com.example.demo.model.BeerDTO;
import com.example.demo.repository.BeerRepository;
import com.example.demo.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll().stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(
                beerMapper.beerToBeerDto(
                        beerRepository.findById(id)
                                .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.
                beerToBeerDto(beerRepository.
                        save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        Optional<Beer> existingBeer = beerRepository.findById(beerId);

        if(existingBeer.isPresent()){
            var foundBeer = existingBeer.get();
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setQuantityOnHand(beer.getQuantityOnHand());

            return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.save(foundBeer)));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        if(beerRepository.existsById(beerId)){
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {

    }
}
