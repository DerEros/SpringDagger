package example.springdagger.central.data.repos;

import reactor.core.publisher.Flux;

import java.util.Map;

public interface IngredientsWithSpecialOffers {
    Flux<Map<String, String>> queryIngredientsAndSpecialOffers();
}
