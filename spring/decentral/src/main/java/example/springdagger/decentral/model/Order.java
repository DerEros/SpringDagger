package example.springdagger.decentral.model;

import java.util.Map;
import java.util.Optional;

public class Order {
    final private Optional<Long> id;
    final private Map<Pizza, Long> pizzaOrders;

    public Order(Long id, Map<Pizza, Long> pizzaOrders) {
        this.id = Optional.of(id);
        this.pizzaOrders = pizzaOrders;
    }

    public Order(Map<Pizza, Long> pizzaOrders) {
        this.id = Optional.empty();
        this.pizzaOrders = pizzaOrders;
    }

    public Optional<Long> getId() {
        return id;
    }

    public Map<Pizza, Long> getPizzaOrders() {
        return pizzaOrders;
    }
}
