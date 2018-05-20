package example.springdagger.decentral.services;

import example.springdagger.decentral.model.Order;
import reactor.core.publisher.Mono;

import javax.inject.Named;

@Named
public class PizzaOrderService {
    public Mono<Void> orderPizza(Mono<Order> order) {
        return Mono.empty();
    }
}
