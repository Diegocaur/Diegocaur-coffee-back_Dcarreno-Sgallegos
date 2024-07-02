package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.TestimonialsEntity;

import java.util.List;

public interface ITestimonialsService {
    TestimonialsEntity createTestimonial(TestimonialsEntity testimonials);
    List<TestimonialsEntity> findByIdCoffee(int idCoffee);
}
