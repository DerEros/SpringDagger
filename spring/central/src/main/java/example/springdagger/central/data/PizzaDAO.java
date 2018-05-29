package example.springdagger.central.data;

import example.springdagger.central.data.repos.PizzaRepo;
import example.springdagger.central.model.Pizza;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PizzaDAO {

    private final PizzaRepo pizzaRepo;

    @Inject
    public PizzaDAO(PizzaRepo pizzaRepo) {
        this.pizzaRepo = pizzaRepo;
    }

    public Flux<Pizza> getPredefPizzas(Integer amount) {
        return Flux.fromIterable(pizzaRepo.findAll()).take(amount);
    }

    public Mono<Pizza> getPredefPizzaById(Long id) {
        return Mono.justOrEmpty(pizzaRepo.findPizzaById(id));
    }
}
