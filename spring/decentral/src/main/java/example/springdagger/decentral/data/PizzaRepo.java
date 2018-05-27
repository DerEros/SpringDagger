package example.springdagger.decentral.data;

import example.springdagger.decentral.model.Pizza;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PizzaRepo extends CrudRepository<Pizza, Long> {
    List<Pizza> findAll();

    Optional<Pizza> findPizzaById(Long id);
}
