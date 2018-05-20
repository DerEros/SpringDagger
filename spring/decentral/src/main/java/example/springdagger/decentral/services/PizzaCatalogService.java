package example.springdagger.decentral.services;

import example.springdagger.decentral.data.IngredientsDAO;
import example.springdagger.decentral.model.Ingredient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PizzaCatalogService {
    private final IngredientsDAO ingredientsDAO;

    @Inject
    public PizzaCatalogService(IngredientsDAO ingredientsDAO) {
        this.ingredientsDAO = ingredientsDAO;
    }

    public Flux<Ingredient> getAllIngredients() {
        return ingredientsDAO.getAllIngredients();
    }

    public Mono<Ingredient> getIngredientById(Long id) {
        return ingredientsDAO.getIngredientById(id);
    }
}
