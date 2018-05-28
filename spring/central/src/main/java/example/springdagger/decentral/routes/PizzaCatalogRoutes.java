package example.springdagger.decentral.routes;

import example.springdagger.decentral.routes.handlers.IngredientsHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

public class PizzaCatalogRoutes {
    private final IngredientsHandler ingredientsHandler;

    public PizzaCatalogRoutes(IngredientsHandler ingredientsHandler) {
        this.ingredientsHandler = ingredientsHandler;
    }

    private RouterFunction createRouteFunction() {
        return RouterFunctions.route(
                GET("/ingredient").and(accept(MediaType.APPLICATION_JSON_UTF8)), this::getAllIngredients);

    }

    private Mono<ServerResponse> getAllIngredients(ServerRequest request) {

    }
}
