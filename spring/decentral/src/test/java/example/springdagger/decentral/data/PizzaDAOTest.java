package example.springdagger.decentral.data;

import example.springdagger.decentral.model.Ingredient;
import example.springdagger.decentral.model.Pizza;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}