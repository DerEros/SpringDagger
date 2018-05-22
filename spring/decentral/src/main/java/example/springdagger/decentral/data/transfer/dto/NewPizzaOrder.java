package example.springdagger.decentral.data.transfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NewPizzaOrder {
    public static class SimplifiedPizza {
        @JsonProperty("ingredients") List<Long> ingredientIDs;

        public List<Long> getIngredientIDs() {
            return ingredientIDs;
        }

        public void setIngredientIDs(List<Long> ingredientIDs) {
            this.ingredientIDs = ingredientIDs;
        }
    }

    public static class PizzaAndAmount {
        SimplifiedPizza pizza;
        Long amount;

        public SimplifiedPizza getPizza() {
            return pizza;
        }

        public void setPizza(SimplifiedPizza pizza) {
            this.pizza = pizza;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }
    }

    private List<PizzaAndAmount> pizzaOrders;

    public List<PizzaAndAmount> getPizzaOrders() {
        return pizzaOrders;
    }

    public void setPizzaOrders(List<PizzaAndAmount> pizzaOrders) {
        this.pizzaOrders = pizzaOrders;
    }
}
