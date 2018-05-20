package example.springdagger.decentral.webclienttest;

import example.springdagger.decentral.services.PizzaCatalogService;
import example.springdagger.decentral.services.PizzaOrderService;
import example.springdagger.decentral.util.FakeIngredients;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@AutoConfigureWebTestClient
@Tag("WebClient")
class PizzaCatalogControllerWebClientTest {

    @Inject private WebTestClient client;

    @MockBean private PizzaCatalogService mockCatalogService;
    @MockBean private PizzaOrderService mockOrderService;

    private FakeIngredients fakeIngredients = new FakeIngredients();

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
}
