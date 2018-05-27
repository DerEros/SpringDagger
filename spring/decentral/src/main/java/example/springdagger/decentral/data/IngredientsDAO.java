package example.springdagger.decentral.data;

import example.springdagger.decentral.data.repos.IngredientsRepo;
import example.springdagger.decentral.model.Ingredient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
public class IngredientsDAO {
    private final IngredientsRepo ingredientsRepo;

    @Inject
    public IngredientsDAO(IngredientsRepo ingredientsRepo) {
        this.ingredientsRepo = ingredientsRepo;
    }

    public Flux<Ingredient> getAllIngredients() {
        return Flux.fromIterable(ingredientsRepo.findAll());
    }

    public Mono<Ingredient> getIngredientById(Long id) {
        return Mono.justOrEmpty(ingredientsRepo.findById(id));
    }

    public Flux<Map<String, String>> getIngredientsWithSpecialOffers() {
        return ingredientsRepo.queryIngredientsAndSpecialOffers();
    }
}
