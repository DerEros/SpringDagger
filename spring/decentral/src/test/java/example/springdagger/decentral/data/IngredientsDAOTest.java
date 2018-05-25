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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
    JdbcTemplate jdbcTemplate;

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
        String query = "SELECT * FROM ingredients AS i JOIN special_offers AS s ON s.ingredients_id=i.id";
        List<String> columnNames = Arrays.asList("ID", "ingredient_type", "name", "price", "description");
        List<Map<String, String>> rows = new ArrayList<>();

        jdbcTemplate.query(query, (result) -> {
            Map<String, String> row = columnNames.stream().collect(Collectors.toMap(Function.identity(), getString(result)));
            rows.add(row);
        });

        assertThat(rows).isEmpty();
    }

    private Function<String, String> getString(ResultSet rs) {
        return (columnName) -> {
            try {
                return rs.getString(columnName);
            } catch (SQLException e) {
                fail("Getting column " + columnName + " from result set should not throw exception");
                throw new IllegalStateException("Should never get here");
            }
        };
    }
}