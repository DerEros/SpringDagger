package example.springdagger.decentral.data;

import reactor.core.publisher.Flux;

import javax.inject.Named;
import example.springdagger.decentral.model.Pizza;

@Named
public class PizzaDAO {
    public Flux<Pizza> getPredefPizza(Integer amount) {
        return Flux.empty();
    }
}
