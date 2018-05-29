package example.springdagger.central.services;

import example.springdagger.central.data.transfer.dto.NewPizzaOrder;
import reactor.core.publisher.Mono;

public class PizzaOrderService {
    public Mono<Void> orderPizza(Mono<NewPizzaOrder> order) {
        return order
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Pizza order may not be empty")))
                .then();
    }
}
