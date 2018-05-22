package example.springdagger.decentral.data.transfer.deserializer;

import example.springdagger.decentral.data.transfer.dto.NewPizzaOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@JsonTest
@AutoConfigureJsonTesters
class NewPizzaOrderDeserializerTest {
    @Inject private JacksonTester<NewPizzaOrder> json;

    @Test
    public void testSimplePizzaOrderIsDeserialized() throws IOException {
        //language=JSON
        String jsonStr = "{\n" +
                "  \"pizzaOrders\": [\n" +
                "    {\n" +
                "      \"pizza\": {\n" +
                "        \"ingredients\": [ 1, 3, 6 ]\n" +
                "      },\n" +
                "      \"amount\": 3\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        NewPizzaOrder result = json.parse(jsonStr).getObject();

        assertThat(result).isNotNull();
        assertThat(result.getPizzaOrders()).size().isEqualTo(1);
        assertThat(result.getPizzaOrders().get(0).getPizza().getIngredientIDs().stream()).contains(1L, 3L, 6L);
        assertThat(result.getPizzaOrders().get(0).getAmount()).isEqualTo(3);
    }
}