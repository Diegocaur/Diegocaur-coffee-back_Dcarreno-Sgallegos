package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.CoffeeEntity;
import cl.ucm.coffee.persitence.repository.CoffeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CoffeeService implements ICoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    public List<CoffeeEntity> listCoffee(){
        return coffeeRepository.findAll();
    }

    @Override
    @Transactional
    public CoffeeEntity createCoffee(CoffeeEntity coffeeEntity) {
            return coffeeRepository.save(coffeeEntity);
    }

    @Override
    public List<CoffeeEntity> findByName(String name) {
            return coffeeRepository.findByName(name);
    }

    @Override
    public void deleteCoffeeById(Integer idCoffee) {
        coffeeRepository.deleteById(idCoffee);
    }


    @Override
    @Transactional
    public Optional<CoffeeEntity> updateCoffee(Integer idCoffee, CoffeeEntity updateCoffee) {
        Optional<CoffeeEntity> existeCoffe =coffeeRepository.findById(idCoffee);
        if (existeCoffe.isPresent()){
                CoffeeEntity coffeeEntity = existeCoffe.get();
                coffeeEntity.setName(updateCoffee.getName());
                coffeeEntity.setPrice(updateCoffee.getPrice());
                coffeeEntity.setDescription(updateCoffee.getDescription());
                coffeeEntity.setImage64(updateCoffee.getImage64());
                return Optional.of(coffeeRepository.save(coffeeEntity));
        } else{
            return Optional.empty();
        }
    }
}
