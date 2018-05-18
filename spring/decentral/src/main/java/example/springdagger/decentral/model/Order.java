package example.springdagger.decentral.model;

import java.util.Map;

public class Order {
    final private Long id;
    final private Map<Pizza, Long> pizzaOrders;

    public Order(Long id, Map<Pizza, Long> pizzaOrders) {
        this.id = id;
        this.pizzaOrders = pizzaOrders;
    }

    public Long getId() {
        return id;
    }

    public Map<Pizza, Long> getPizzaOrders() {
        return pizzaOrders;
    }
}
