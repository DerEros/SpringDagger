package example.springdagger.decentral.services;

import example.springdagger.decentral.data.transfer.dto.NewPizzaOrder;
import reactor.core.publisher.Mono;

import javax.inject.Named;

@Named
public class PizzaOrderService {
    public Mono<Void> orderPizza(Mono<NewPizzaOrder> order) {
        return order
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Pizza order may not be empty")))
                .then();
    }
}
