package example.springdagger.decentral.controller;

import example.springdagger.decentral.data.IngredientsDAO;
import example.springdagger.decentral.model.Ingredient;
import example.springdagger.decentral.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PizzaCatalogController.class })
class PizzaCatalogControllerTest {
    @Inject
    private PizzaCatalogController pizzaCatalogController;

    @MockBean
    private IngredientsDAO ingredientsDAO;

    private List<Ingredient> dummyIngredients = createDummyIngredients();

    private List<Ingredient> createDummyIngredients() {
        return Arrays.asList(new Ingredient(1L, Ingredient.Type.DOUGH, "foo", 1.23f),
                new Ingredient(2L, Ingredient.Type.SAUCE, "bar", 2.34f),
                new Ingredient(3L, Ingredient.Type.TOPPING, "baz", 3.45f)
        );
    }

    @Test
    void testControllerIsInstantiated() {
        assertThat(pizzaCatalogController).isNotNull();
    }

    @Test
    void testIngredientsAllIngredientsAreReturnedAtOnce() {
        given(ingredientsDAO.getAllIngredients()).willReturn(Flux.fromIterable(dummyIngredients));

        List<Ingredient> ingredients = pizzaCatalogController.getIngredients().collectList().block();
        assertThat(ingredients).extracting("name").contains("foo", "bar", "baz");
    }

    @Test
    void testOneIngredientReturnedById() {
        given(ingredientsDAO.getIngredientById(2L)).willReturn(Mono.just(dummyIngredients.get(1)));

        Ingredient ingredient = pizzaCatalogController.getIngredientById(2L).block();
        assertThat(ingredient.getName()).isEqualTo("bar");
    }

    @Test
    void testPizzaOrderCanBePlaced() {
        Order order = new Order(42L, new HashMap<>());
        assertThat(pizzaCatalogController.placeOrder(Mono.just(order))).isEqualTo(Mono.empty());
    }
}