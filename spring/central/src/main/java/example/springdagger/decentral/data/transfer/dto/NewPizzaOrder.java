package example.springdagger.decentral.data.transfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NewPizzaOrder {
    private final List<PizzaAndAmount> pizzaOrders;

    public NewPizzaOrder(@JsonProperty(value = "pizzaOrders", required = true) List<PizzaAndAmount> pizzaOrders) {
        this.pizzaOrders = pizzaOrders;
    }

    public List<PizzaAndAmount> getPizzaOrders() {
        return pizzaOrders;
    }
}
