package example.springdagger.central.data;

import example.springdagger.central.model.Ingredient;
import example.springdagger.central.model.Pizza;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PizzaDAO.class, RepositoryConfig.class})
@DataJpaTest
@EntityScan(basePackageClasses = {Pizza.class})
@Sql({"Pizza.sql"})
@Tag("DBTest")
class PizzaDAOTest {

    @Inject
    PizzaDAO pizzaDAO;

    @Test
    void testLoadDDL() {
    }

    @Test
    void testLoadPizzaSalami() {
        Pizza pizza = pizzaDAO.getPredefPizzaById(2L).block();

        assertThat(pizza.getName()).isEqualTo("Pizza Salami");
        assertThat(pizza.getIngredients().stream().map(Ingredient::getName))
                .containsOnly("Thin Dough", "Tomato Sauce", "Salami");
    }

    @Test
    void testLoadOnePizza() {
        Flux<Pizza> pizzas = pizzaDAO.getPredefPizzas(1);
        StepVerifier.create(pizzas)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testLoadThreePizza() {
        Flux<Pizza> pizzas = pizzaDAO.getPredefPizzas(3);
        StepVerifier.create(pizzas)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void testGetMaxAmountOfPizza() {
        Flux<Pizza> pizzas = pizzaDAO.getPredefPizzas(Integer.MAX_VALUE);
        StepVerifier.create(pizzas)
                .expectNextCount(4)
                .verifyComplete();
    }
}