package example.springdagger.decentral.data;

import example.springdagger.decentral.model.Ingredient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Named;

@Named
public class IngredientsDAO {
    public Flux<Ingredient> getAllIngredients() {
        return Flux.empty();
    }

    public Mono<Ingredient> getIngredientById(Long id) {
        return Mono.empty();
    }
}
