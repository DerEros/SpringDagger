package example.springdagger.decentral.controller;

import example.springdagger.decentral.data.IngredientsDAO;
import example.springdagger.decentral.model.Ingredient;
import example.springdagger.decentral.model.Order;
import example.springdagger.decentral.util.FakeIngredients;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PizzaCatalogController.class, FakeIngredients.class })
@Tag("UnitTest")
class PizzaCatalogControllerTest {
    @Inject
    private PizzaCatalogController pizzaCatalogController;

    @MockBean
    private IngredientsDAO ingredientsDAO;

    private FakeIngredients fakeIngredients = new FakeIngredients();

    @Test
    void testControllerIsInstantiated() {
        assertThat(pizzaCatalogController).isNotNull();
    }

    @Test
    void testIngredientsAllIngredientsAreReturnedAtOnce() {
        given(ingredientsDAO.getAllIngredients()).willReturn(Flux.fromIterable(fakeIngredients.getAllIngredients()));

        List<Ingredient> ingredients = pizzaCatalogController.getIngredients().collectList().block();
        assertThat(ingredients)
                .extracting("name")
                .contains("Thin Dough", "Thick Dough", "Tomato Sauce", "BBQ Sauce", "Cheese", "Salami");
    }

    @Test
    void testAllIngredientsWhenNoneAreAvailable() {
        given(ingredientsDAO.getAllIngredients()).willReturn(Flux.empty());

        List<Ingredient> ingredients = pizzaCatalogController.getIngredients().collectList().block();
        assertThat(ingredients).isEmpty();
    }

    @Test
    void testOneIngredientReturnedById() {
        given(ingredientsDAO.getIngredientById(2L)).willReturn(Mono.just(fakeIngredients.getIngredientById(2L)));

        Ingredient ingredient = pizzaCatalogController.getIngredientById(2L).map(ResponseEntity::getBody).block();
        assertThat(ingredient.getName()).isEqualTo("Thick Dough");
    }

    @Test
    void testOneIngredientWithNonExistingId() {
        given(ingredientsDAO.getIngredientById(2L)).willReturn(Mono.empty());
        assertThat(pizzaCatalogController.getIngredientById(2L).block().getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    void testPizzaOrderCanBePlaced() {
        Order order = new Order(42L, new HashMap<>());
        assertThat(pizzaCatalogController.placeOrder(Mono.just(order))).isEqualTo(Mono.empty());
    }
}