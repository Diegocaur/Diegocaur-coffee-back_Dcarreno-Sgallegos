package cl.ucm.coffee.web.controller;


import cl.ucm.coffee.persitence.entity.CoffeeEntity;
import cl.ucm.coffee.service.ICoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@RestController
@RequestMapping("/api/coffee")
public class CoffeeController {

    @Autowired
    private ICoffeeService coffeeService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearCoffes(
            @RequestParam(name="name") String name,
            @RequestParam(name= "price") int price,
            @RequestParam(name="desc") String description,
            @RequestParam(name="foto") MultipartFile image64
            ){
        try {
            CoffeeEntity coffeeEntity = new CoffeeEntity();
            coffeeEntity.setName(name);
            coffeeEntity.setPrice(price);
            coffeeEntity.setDescription(description);
            coffeeEntity.setImage64(image64.getBytes());

            CoffeeEntity crearCoffee =coffeeService.createCoffee(coffeeEntity);
            return ResponseEntity.ok(crearCoffee);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error al crear café:"+ e.getMessage());
        }
    }


    @GetMapping("/coffename")
    public ResponseEntity<?> findByName(@RequestParam(name = "name") String name){

        try {
            List<CoffeeEntity> coffesName = coffeeService.findByName(name);
            return ResponseEntity.ok(coffesName);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error buscando por nombre:"+ e.getMessage());
        }
    }

    @GetMapping("listacoffes")
    public ResponseEntity<List<CoffeeEntity>> listcoffee(){
        try {
            List<CoffeeEntity> listacoffes = coffeeService.listCoffee();
            return ResponseEntity.ok(listacoffes);
        }catch (Exception e){
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }


    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarCoffee(
            @RequestParam(name = "id") Integer idCoffe,
            @RequestParam(name="name") String name,
            @RequestParam(name= "price") int price,
            @RequestParam(name="desc") String description,
            @RequestParam(name="foto")MultipartFile image64 ){
    try {
        CoffeeEntity actualizarCoffe = new CoffeeEntity();
        actualizarCoffe.setIdCoffee(idCoffe);
        actualizarCoffe.setName(name);
        actualizarCoffe.setPrice(price);
        actualizarCoffe.setDescription(description);
        actualizarCoffe.setImage64(image64.getBytes());

        Optional<CoffeeEntity> actualizado =coffeeService.updateCoffee(idCoffe, actualizarCoffe);
        return ResponseEntity.ok(actualizado.isPresent() ? actualizado.get(): "No existe cafe con ese id");
    }catch (Exception e){
        return ResponseEntity.status(500).body("Error al actualizar por:"+ e.getMessage());
    }
    }


    @DeleteMapping("borrar/{id}")
    public ResponseEntity<String> deleteCoffee(@PathVariable Integer id){
        try {
            coffeeService.deleteCoffeeById(id);
            return ResponseEntity.ok("eliminado con exito");
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error al eliminar cafe:"+ e.getMessage());
        }

    }

    @GetMapping("")
    public ResponseEntity<Map<String, String>> coffes(){
        Map map = new HashMap();
        map.put("coffee", "Coffees :Get)");
        return ResponseEntity.ok(map);
    }
    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> coffe(){
        Map map = new HashMap();
        map.put("coffee", "Coffees Post:)");
        return ResponseEntity.ok(map);
    }

}
