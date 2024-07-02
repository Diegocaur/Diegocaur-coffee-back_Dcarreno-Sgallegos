package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.TestimonialsEntity;
import cl.ucm.coffee.persitence.repository.TestimonialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestimonialsService implements ITestimonialsService {

    @Autowired
    private TestimonialsRepository testimonialsRepository;


    @Override
    public TestimonialsEntity createTestimonial( TestimonialsEntity testimonials){
        return testimonialsRepository.save(testimonials);
    }

    @Override
    public List<TestimonialsEntity> findByIdCoffee(int idCoffee) {
        return testimonialsRepository.findByIdCoffee(idCoffee);
    }

}
