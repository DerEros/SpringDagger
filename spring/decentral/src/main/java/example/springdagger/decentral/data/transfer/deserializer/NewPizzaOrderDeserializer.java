package example.springdagger.decentral.data.transfer.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.springdagger.decentral.data.transfer.dto.NewPizzaOrder;
import example.springdagger.decentral.model.Pizza;
import org.springframework.boot.jackson.JsonComponent;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@JsonComponent
public class NewPizzaOrderDeserializer extends JsonDeserializer<NewPizzaOrder> {
    @Inject ObjectMapper defaultMapper;

    @Override
    public NewPizzaOrder deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        JsonNode jsonTree = parser.getCodec().readTree(parser);

        JsonNode pizzaOrders = jsonTree.get("pizzaOrders");
        if (pizzaOrders.isArray()) {
            List<NewPizzaOrder.PizzaAndAmount> pizzaAndAmounts = StreamSupport.stream(pizzaOrders.spliterator(), true)
                    .map(node -> new NewPizzaOrder.PizzaAndAmount(
                            defaultMapper.convertValue(node.get("pizza"), NewPizzaOrder.SimplifiedPizza.class),
                            node.get("amount").asLong()
                    ))
                    .collect(Collectors.toList());

            return new NewPizzaOrder(pizzaAndAmounts);
        } else {
            throw new RuntimeException("Property pizzaOrders not found or is not an array");
        }
    }


}
