package example.springdagger.decentral.data;

import reactor.core.publisher.Flux;

import java.util.Map;

public interface IngredientsWithSpecialOffers {
    Flux<Map<String, String>> queryIngredientsAndSpecialOffers();
}
