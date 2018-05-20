package example.springdagger.decentral.data.transfer.dto;

import example.springdagger.decentral.model.Pizza;

import java.util.List;

public class NewPizzaOrder {
    public static class PizzaAndAmount {
        Pizza pizza;
        Long amount;

        public PizzaAndAmount(Pizza pizza, Long amount) {
            this.pizza = pizza;
            this.amount = amount;
        }

        public Pizza getPizza() {
            return pizza;
        }

        public Long getAmount() {
            return amount;
        }
    }

    private final List<PizzaAndAmount> pizzaOrders;

    public NewPizzaOrder(List<PizzaAndAmount> pizzaOrders) {
        this.pizzaOrders = pizzaOrders;
    }

    public List<PizzaAndAmount> getPizzaOrders() {
        return pizzaOrders;
    }


}
