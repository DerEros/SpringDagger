package example.springdagger.central.services;

import example.springdagger.central.data.IngredientsDAO;
import example.springdagger.central.data.PizzaDAO;
import example.springdagger.central.model.Ingredient;
import example.springdagger.central.model.Pizza;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PizzaCatalogService {
    private final IngredientsDAO ingredientsDAO;
    private final PizzaDAO pizzaDAO;

    @Inject
    public PizzaCatalogService(IngredientsDAO ingredientsDAO, PizzaDAO pizzaDAO) {
        this.ingredientsDAO = ingredientsDAO;
        this.pizzaDAO = pizzaDAO;
    }

    public Flux<Ingredient> getAllIngredients() {
        return ingredientsDAO.getAllIngredients();
    }

    public Mono<Ingredient> getIngredientById(Long id) {
        return ingredientsDAO.getIngredientById(id);
    }

    public Flux<Pizza> getPredefPizzas(Integer amount) {
        return pizzaDAO.getPredefPizzas(amount);
    }
}
