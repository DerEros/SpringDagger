package example.springdagger.decentral.data;

import example.springdagger.decentral.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientsRepo extends CrudRepository<Ingredient, Long>, IngredientsWithSpecialOffers {
    List<Ingredient> findAll();

    Optional<Ingredient> findById(Long id);
}
