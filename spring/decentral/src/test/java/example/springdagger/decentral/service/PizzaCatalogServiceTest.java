package example.springdagger.decentral.service;

import example.springdagger.decentral.data.IngredientsDAO;
import example.springdagger.decentral.data.PizzaDAO;
import example.springdagger.decentral.model.Ingredient;
import example.springdagger.decentral.model.Pizza;
import example.springdagger.decentral.services.PizzaCatalogService;
import example.springdagger.decentral.util.FakeIngredients;
import example.springdagger.decentral.util.FakePizzas;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PizzaCatalogService.class, FakeIngredients.class })
@Tag("UnitTest")
class PizzaCatalogServiceTest {
    @Inject private PizzaCatalogService pizzaCatalogService;

    @MockBean private IngredientsDAO ingredientsDAO;
    @MockBean private PizzaDAO pizzaDAO;
    
    private final FakeIngredients fakeIngredients = new FakeIngredients();
    private final FakePizzas fakePizzas = new FakePizzas();

    @Test
    void testControllerIsInstantiated() {
        assertThat(pizzaCatalogService).isNotNull();
    }

    @Test
    void testIngredientsAllIngredientsAreReturnedAtOnce() {
        given(ingredientsDAO.getAllIngredients()).willReturn(Flux.fromIterable(fakeIngredients.getAllIngredients()));

        List<Ingredient> ingredients = pizzaCatalogService.getAllIngredients().collectList().block();
        assertThat(ingredients)
                .extracting("name")
                .contains("Thin Dough", "Thick Dough", "Tomato Sauce", "BBQ Sauce", "Cheese", "Salami");
    }

    @Test
    void testAllIngredientsWhenNoneAreAvailable() {
        given(ingredientsDAO.getAllIngredients()).willReturn(Flux.empty());

        List<Ingredient> ingredients = pizzaCatalogService.getAllIngredients().collectList().block();
        assertThat(ingredients).isEmpty();
    }

    @Test
    void testOneIngredientReturnedById() {
        given(ingredientsDAO.getIngredientById(2L)).willReturn(Mono.just(fakeIngredients.getIngredientById(2L)));

        Ingredient ingredient = pizzaCatalogService.getIngredientById(2L).block();
        assertThat(ingredient.getName()).isEqualTo("Thick Dough");
    }

    @Test
    void testOneIngredientWithNonExistingId() {
        given(ingredientsDAO.getIngredientById(2L)).willReturn(Mono.empty());
        assertThat(pizzaCatalogService.getIngredientById(2L)).isEqualTo(Mono.empty());
    }

    @Test
    void testRequestPredefPizza() {
        Pizza pizza = fakePizzas.getPizza(1);
        given(pizzaDAO.getPredefPizzas(any())).willReturn(Flux.just(pizza));

        StepVerifier.create(pizzaCatalogService.getPredefPizzas(1)).expectNext(pizza).verifyComplete();
    }

    @Test
    void testRequestNonExistingPredefPizza() {
        given(pizzaDAO.getPredefPizzas(any())).willReturn(Flux.empty());

        StepVerifier.create(pizzaCatalogService.getPredefPizzas(4711)).verifyComplete();
    }
}
