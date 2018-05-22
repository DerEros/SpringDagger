package example.springdagger.decentral.data.transfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NewPizzaOrder {
    public static class SimplifiedPizza {
        private final List<Long> ingredientIDs;

        public SimplifiedPizza(@JsonProperty("ingredients") List<Long> ingredientIDs) {
            this.ingredientIDs = ingredientIDs;
        }

        public List<Long> getIngredientIDs() {
            return ingredientIDs;
        }
    }

    public static class PizzaAndAmount {
        private final SimplifiedPizza pizza;
        private final Long amount;

        public PizzaAndAmount(@JsonProperty("pizza") SimplifiedPizza pizza, @JsonProperty("amount") Long amount) {
            this.pizza = pizza;
            this.amount = amount;
        }

        public SimplifiedPizza getPizza() {
            return pizza;
        }

        public Long getAmount() {
            return amount;
        }
    }

    private final List<PizzaAndAmount> pizzaOrders;

    public NewPizzaOrder(@JsonProperty("pizzaOrders") List<PizzaAndAmount> pizzaOrders) {
        this.pizzaOrders = pizzaOrders;
    }

    public List<PizzaAndAmount> getPizzaOrders() {
        return pizzaOrders;
    }
}
