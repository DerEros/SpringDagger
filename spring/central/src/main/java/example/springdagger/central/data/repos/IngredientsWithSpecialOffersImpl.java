package example.springdagger.central.data.repos;

import org.springframework.jdbc.core.JdbcTemplate;
import reactor.core.publisher.Flux;

import javax.inject.Inject;
import java.util.Map;
import java.util.stream.Collectors;

public class IngredientsWithSpecialOffersImpl implements IngredientsWithSpecialOffers {
    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public Flux<Map<String, String>> queryIngredientsAndSpecialOffers() {
        String query = "SELECT * FROM ingredients AS i JOIN special_offers AS s ON s.ingredients_id=i.id " +
                "ORDER BY i.id, s.id ASC";

        return Flux.fromIterable(jdbcTemplate.queryForList(query))
                .map(row -> row.entrySet().stream()
                        .collect(
                                Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString())
                        )
                );
    }
}
