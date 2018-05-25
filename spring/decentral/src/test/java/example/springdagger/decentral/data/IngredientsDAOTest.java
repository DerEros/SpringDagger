package example.springdagger.decentral.data;

import example.springdagger.decentral.model.Ingredient;
import example.springdagger.decentral.util.FakeIngredients;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {IngredientsDAO.class, RepositoryConfig.class})
@DataJpaTest
@EntityScan(basePackageClasses = {Ingredient.class})
@Tag("DBTest")
class IngredientsDAOTest {
    private final FakeIngredients fakeIngredients = new FakeIngredients();
    @Inject
    private IngredientsDAO ingredientsDAO;
    @Inject
    private TestEntityManager entityManager;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Test
    void testRetrieveAllIngredients() {
        String[] expectedIngredientNames = addFakeIngredientsToDatabase();

        Flux<Ingredient> foundIngredients = ingredientsDAO.getAllIngredients();
        StepVerifier.create(foundIngredients.map(Ingredient::getName))
                .expectNext(expectedIngredientNames)
                .verifyComplete();
    }

    private String[] addFakeIngredientsToDatabase() {
        String[] expectedIngredientNames = fakeIngredients.getAllIngredients().stream()
                .map(Ingredient::getName)
                .toArray(String[]::new);

        fakeIngredients.getAllIngredients().forEach(entityManager::persist);
        entityManager.flush();
        return expectedIngredientNames;
    }

    @Test
    void testRetrieveIngredientsWithEmptyDB() {
        Flux<Ingredient> foundIngredients = ingredientsDAO.getAllIngredients();
        StepVerifier.create(foundIngredients).verifyComplete();
    }

    @Test
    void testRetrieveIngredientById() {
        addFakeIngredientsToDatabase();
        Mono<Ingredient> foundIngredient = ingredientsDAO.getIngredientById(1L);

        StepVerifier.create(foundIngredient).expectNext(fakeIngredients.getIngredientById(1L)).verifyComplete();
    }

    @Test
    void testRetrieveIngredientByNonExistingId() {
        addFakeIngredientsToDatabase();
        Mono<Ingredient> foundIngredient = ingredientsDAO.getIngredientById(4223L);

        StepVerifier.create(foundIngredient).verifyComplete();
    }

    @Test
    @Sql({"IngredientsAndSpecialOffer.sql"})
    void testIngredientsWithSpecialOffers() {
        Flux<Map<String, String>> ingredientsWithSpecialOffers = ingredientsDAO.getIngredientsWithSpecialOffers();

        List<Map<String, String>> rows = ingredientsWithSpecialOffers.toStream().collect(Collectors.toList());

        assertThat(rows).hasSize(4);
        assertThat(rows.stream().map(r -> r.get("ID")).collect(Collectors.toList())).containsOnly("2", "5", "5", "6");
        assertThat(rows.stream().map(r -> r.get("description")).collect(Collectors.toList()))
                .containsOnly("Thick Dough for the price of thin dough",
                        "Double cheese gratis",
                        "Mozzarella for the same price",
                        "Make it hot!");
    }

    @Test
    @Sql({"IngredientsAndSpecialOfferWithBrokenTable.sql"})
    void testIngredientsWithSpecialOffersWithBrokenTable() {
        assertThatThrownBy(ingredientsDAO::getIngredientsWithSpecialOffers)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Getting column price from result set should not throw exception");
    }

}