package example.springdagger.decentral.data.transfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NewPizzaOrder {
    private final List<PizzaAndAmount> pizzaOrders;

    public NewPizzaOrder(@JsonProperty(value = "pizzaOrders", required = true) List<PizzaAndAmount> pizzaOrders) {
        this.pizzaOrders = pizzaOrders;
    }

    public static class SimplifiedPizza {
        private final List<Long> ingredientIDs;

        public SimplifiedPizza(@JsonProperty(value = "ingredients", required = true) List<Long> ingredientIDs) {
            this.ingredientIDs = ingredientIDs;
        }

        public List<Long> getIngredientIDs() {
            return ingredientIDs;
        }
    }

    public static class PizzaAndAmount {
        private final SimplifiedPizza pizza;
        private final Long amount;

        public PizzaAndAmount(@JsonProperty(value = "pizza", required = true) SimplifiedPizza pizza,
                              @JsonProperty(value = "amount", required = true) Long amount) {
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

    public List<PizzaAndAmount> getPizzaOrders() {
        return pizzaOrders;
    }
}
