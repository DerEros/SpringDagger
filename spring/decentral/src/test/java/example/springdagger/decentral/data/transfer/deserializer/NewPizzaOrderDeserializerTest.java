package example.springdagger.decentral.data.transfer.deserializer;

import example.springdagger.decentral.data.transfer.dto.NewPizzaOrder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@JsonTest
@AutoConfigureJsonTesters
@Tag("UnitTest")
class NewPizzaOrderDeserializerTest {
    @Inject private JacksonTester<NewPizzaOrder> json;

    @Test
    void testSimplePizzaOrderIsDeserialized() throws IOException {
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
        assertThat(result.getPizzaOrders().get(0).getPizza().getIngredientIDs()).contains(1L, 3L, 6L);
        assertThat(result.getPizzaOrders().get(0).getAmount()).isEqualTo(3);
    }

    @Test
    void testDeserializePizzaWithoutIngredients() throws IOException {
        //language=JSON
        String jsonStr = "{\n" +
                "  \"pizzaOrders\": [\n" +
                "    {\n" +
                "      \"pizza\": {\n" +
                "        \"ingredients\": []\n" +
                "      },\n" +
                "      \"amount\": 3\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        NewPizzaOrder result = json.parse(jsonStr).getObject();

        assertThat(result).isNotNull();
        assertThat(result.getPizzaOrders().get(0).getPizza().getIngredientIDs()).isEmpty();
    }

    @Test
    void testDeserializeOrderWithMultiplePizzas() throws IOException {
        //language=JSON
        String jsonStr = "{\n" +
                "  \"pizzaOrders\": [\n" +
                "    {\n" +
                "      \"pizza\": {\n" +
                "        \"ingredients\": [ 1, 3, 5 ]\n" +
                "      },\n" +
                "      \"amount\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"pizza\": {\n" +
                "        \"ingredients\": [ 2, 4, 6 ]\n" +
                "      },\n" +
                "      \"amount\": 5\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        NewPizzaOrder result = json.parse(jsonStr).getObject();

        assertThat(result).isNotNull();
        assertThat(result.getPizzaOrders().get(0).getPizza().getIngredientIDs()).contains(1L, 3L, 5L);
        assertThat(result.getPizzaOrders().get(0).getAmount()).isEqualTo(3);
        assertThat(result.getPizzaOrders().get(1).getPizza().getIngredientIDs()).contains(2L, 4L, 6L);
        assertThat(result.getPizzaOrders().get(1).getAmount()).isEqualTo(5);
    }

    @Test
    void testEmptyOrder() throws IOException {
        //language=JSON
        String jsonStr = "{\n" +
                "  \"pizzaOrders\": []\n" +
                "}";

        NewPizzaOrder result = json.parse(jsonStr).getObject();

        assertThat(result).isNotNull();
        assertThat(result.getPizzaOrders()).isEmpty();
    }

    @Test
    void testDTOHasAdditionalFields() throws IOException {
        //language=JSON
        String jsonStr = "{\n" +
                "  \"foo\": 3,\n" +
                "  \"pizzaOrders\": [\n" +
                "    {\n" +
                "      \"bar\": \"blabla\",\n" +
                "      \"pizza\": {\n" +
                "        \"baz\": {\n" +
                "          \"ingredients\": [ 2 ]\n" +
                "        },\n" +
                "        \"ingredients\": [ 1, 3, 6 ]\n" +
                "      },\n" +
                "      \"amount\": 3,\n" +
                "      \"pazza\": {\n" +
                "        \"amount\": [ 47, 11, 8, 15 ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        NewPizzaOrder result = json.parse(jsonStr).getObject();

        assertThat(result).isNotNull();
        assertThat(result.getPizzaOrders()).size().isEqualTo(1);
        assertThat(result.getPizzaOrders().get(0).getPizza().getIngredientIDs()).contains(1L, 3L, 6L);
        assertThat(result.getPizzaOrders().get(0).getAmount()).isEqualTo(3);
    }

    @Test
    @Disabled
    void testOrderLacksMandatoryField() {
        //language=JSON
        String jsonStr = "{\n" +
                "  \"pizzaOrders\": [\n" +
                "    {\n" +
                "      \"pizza\": {\n" +
                "        \"ingredients\": [ 1, 3, 6 ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        assertThatThrownBy(() -> { NewPizzaOrder result = json.parse(jsonStr).getObject(); })
                .isInstanceOf(ParseException.class);
    }
}