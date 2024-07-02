package cl.ucm.coffee.web.controller;


import cl.ucm.coffee.persitence.entity.TestimonialsEntity;
import cl.ucm.coffee.service.ITestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testimonial")
public class TestimonialsController {
    @Autowired
    private ITestimonialsService testimonialsService;


    @PostMapping("/cre")
    public ResponseEntity<?> crear(@RequestBody TestimonialsEntity testimonial){
        try {
            TestimonialsEntity crearTestimonial =testimonialsService.createTestimonial(testimonial);
            return ResponseEntity.ok(crearTestimonial);
        } catch (Exception e){
            return ResponseEntity.status(500).body("No se pudo crear testimonio por:" + e.getMessage());
        }
    }
    @PostMapping("/crear")
    public ResponseEntity<?> crearse(
            @RequestParam("username") String username,
            @RequestParam("testimonial") String testimonial,
            @RequestParam("idCoffee") int idCoffee) {
        try {
            TestimonialsEntity testimonialEntity = new TestimonialsEntity();
            testimonialEntity.setUsername(username);
            testimonialEntity.setTestimonial(testimonial);
            testimonialEntity.setIdCoffee(idCoffee);

            TestimonialsEntity createdTestimonial = testimonialsService.createTestimonial(testimonialEntity);
            return ResponseEntity.ok(createdTestimonial);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("No se pudo crear testimonio por:" + e.getMessage());
        }
    }

    @GetMapping("coffeeid")
    public ResponseEntity<?> findByCoffeeId(@RequestParam(name = "coffeeId") int coffeeId){

        try {

            List<TestimonialsEntity> testimonio = testimonialsService.findByIdCoffee(coffeeId);
            return ResponseEntity.ok(testimonio);

        }catch (Exception e){
            return  ResponseEntity.status(500).body("No se encontró testimonio de algun caffe con ese ID:"+ e.getMessage());
        }
    }


}
