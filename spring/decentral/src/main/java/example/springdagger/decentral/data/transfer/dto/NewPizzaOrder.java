package example.springdagger.decentral.data.transfer.dto;

import example.springdagger.decentral.model.Pizza;

import java.util.List;

public class NewPizzaOrder {
    public static class SimplifiedPizza {
        List<Long> ingredientIDs;

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

    private final List<PizzaAndAmount> pizzaOrders;

    public NewPizzaOrder(List<PizzaAndAmount> pizzaOrders) {
        this.pizzaOrders = pizzaOrders;
    }

    public List<PizzaAndAmount> getPizzaOrders() {
        return pizzaOrders;
    }


}
