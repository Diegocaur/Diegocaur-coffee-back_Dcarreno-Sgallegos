package cl.ucm.coffee.service;


import cl.ucm.coffee.persitence.entity.CoffeeEntity;

import java.util.List;
import java.util.Optional;

public interface ICoffeeService {
    List<CoffeeEntity> listCoffee();
    CoffeeEntity createCoffee(CoffeeEntity coffeeEntity);
    List<CoffeeEntity> findByName(String name);
    void deleteCoffeeById(Integer idCoffee);
    Optional<CoffeeEntity> updateCoffee(Integer idCoffee, CoffeeEntity updateCoffee);
}
