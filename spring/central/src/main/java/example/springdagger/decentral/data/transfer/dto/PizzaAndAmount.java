package example.springdagger.decentral.data.transfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PizzaAndAmount {
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
