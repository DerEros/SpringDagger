package example.springdagger.decentral.webclienttest;

import example.springdagger.decentral.model.Pizza;
import example.springdagger.decentral.services.PizzaCatalogService;
import example.springdagger.decentral.services.PizzaOrderService;
import example.springdagger.decentral.util.FakeIngredients;
import example.springdagger.decentral.util.FakePizzas;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@AutoConfigureWebTestClient
@AutoConfigureJson
@Tag("WebClient")
class PizzaCatalogControllerWebClientTest {

    @Inject private WebTestClient client;

    @MockBean private PizzaCatalogService mockCatalogService;
    @MockBean private PizzaOrderService mockOrderService;

    private final FakeIngredients fakeIngredients = new FakeIngredients();
    private final FakePizzas fakePizzas = new FakePizzas();

    @Test
    void testReadAllIngredients() {
        String expectedResult = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"ingredientType\": \"DOUGH\",\n" +
                "    \"name\": \"Thin Dough\",\n" +
                "    \"price\": 1.00\n" +
                "  }, {\n" +
                "    \"id\": 2,\n" +
                "    \"ingredientType\": \"DOUGH\",\n" +
                "    \"name\": \"Thick Dough\",\n" +
                "    \"price\": 1.50\n" +
                "  }, {\n" +
                "    \"id\": 3,\n" +
                "    \"ingredientType\": \"SAUCE\",\n" +
                "    \"name\": \"Tomato Sauce\",\n" +
                "    \"price\": 0.50\n" +
                "  }, {\n" +
                "    \"id\": 4,\n" +
                "    \"ingredientType\": \"SAUCE\",\n" +
                "    \"name\": \"BBQ Sauce\",\n" +
                "    \"price\": 0.70\n" +
                "  }, {\n" +
                "    \"id\": 5,\n" +
                "    \"ingredientType\": \"TOPPING\",\n" +
                "    \"name\": \"Cheese\",\n" +
                "    \"price\": 0\n" +
                "  }, {\n" +
                "    \"id\": 6,\n" +
                "    \"ingredientType\": \"TOPPING\",\n" +
                "    \"name\": \"Salami\",\n" +
                "    \"price\": 0.50\n" +
                "  }\n" +
                "]";

        given(mockCatalogService.getAllIngredients())
                .willReturn(Flux.fromIterable(fakeIngredients.getAllIngredients()));
        client.get().uri("/ingredients").exchange().expectStatus().isOk().expectBody().json(expectedResult);
    }

    @Test
    void testReadAllIngredientsWhenNoneAvailable() {
        given(mockCatalogService.getAllIngredients()).willReturn(Flux.empty());
        client.get().uri("/ingredients").exchange().expectStatus().isOk().expectBody().json("[]");
    }

    @Test
    void testRequestIngredientById() {
        String expectedResult = "  {\n" +
                "    \"id\": 4,\n" +
                "    \"ingredientType\": \"SAUCE\",\n" +
                "    \"name\": \"BBQ Sauce\",\n" +
                "    \"price\": 0.70\n" +
                "  }\n";

        given(mockCatalogService.getIngredientById(4L))
                .willReturn(Mono.just(fakeIngredients.getIngredientById(4L)));
        client.get().uri("/ingredients/4").exchange().expectStatus().isOk().expectBody().json(expectedResult);
    }

    @Test
    void testRequestNonExistingIngredientById() {
        given(mockCatalogService.getIngredientById(9L)).willReturn(Mono.empty());
        client.get().uri("/ingredients/9").exchange().expectStatus().isNotFound();
    }

    @Test
    void testPlacePizzaOrder() {
        given(mockOrderService.orderPizza(any())).willReturn(Mono.empty());

        String order = "{\n" +
                "  \"pizzaOrders\": [\n" +
                "    {\n" +
                "      \"pizza\": {\n" +
                "        \"ingredients\": [ 1, 3, 6 ]\n" +
                "      },\n" +
                "      \"amount\": 3\n" +
                "    } \n" +
                "  ]\n" +
                "}";

        client.post().uri("/order").contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(order))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void testRequestUnspecifiedAmountOfPizzas() {
        Pizza pizza1 = fakePizzas.getPizza(0);
        //language=JSON
        String expected="[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"ingredients\": [\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"ingredientType\": \"DOUGH\",\n" +
                "        \"name\": \"Thin Dough\",\n" +
                "        \"price\": 1.0\n" +
                "      }, {\n" +
                "        \"id\": 3,\n" +
                "        \"ingredientType\": \"SAUCE\",\n" +
                "        \"name\": \"Tomato Sauce\",\n" +
                "        \"price\": 0.5\n" +
                "      }, {\n" +
                "        \"id\": 5,\n" +
                "        \"ingredientType\": \"TOPPING\",\n" +
                "        \"name\": \"Cheese\",\n" +
                "        \"price\": 0.0\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        given(mockCatalogService.getPredefPizzas(any())).willReturn(Flux.just(pizza1));

        client.get().uri("/pizza").exchange().expectStatus().isOk().expectBody().json(expected);

        then(mockCatalogService).should().getPredefPizzas(10);
    }

    @Test
    void testRequestFivePizzas() {
        given(mockCatalogService.getPredefPizzas(any())).willReturn(Flux.empty());

        client.get().uri("/pizza/?amount=5").exchange().expectStatus().isOk().expectBody().json("[]");

        then(mockCatalogService).should().getPredefPizzas(5);
    }
}
