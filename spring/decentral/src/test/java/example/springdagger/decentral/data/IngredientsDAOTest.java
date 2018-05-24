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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import javax.inject.Inject;

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

    @Test
    void testRetrieveAllIngredients() {
        String[] expectedIngredientNames = fakeIngredients.getAllIngredients().stream()
                .map(Ingredient::getName)
                .toArray(String[]::new);

        fakeIngredients.getAllIngredients().forEach(entityManager::persist);
        entityManager.flush();

        Flux<Ingredient> foundIngredients = ingredientsDAO.getAllIngredients();
        StepVerifier.create(foundIngredients.map(Ingredient::getName))
                .expectNext(expectedIngredientNames)
                .verifyComplete();
    }
}