package example.springdagger.central.routes;

import example.springdagger.central.routes.handlers.IngredientsHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

public class PizzaCatalogRoutes {
    private final IngredientsHandler ingredientsHandler;
    private final RouterFunction routes;

    public PizzaCatalogRoutes(IngredientsHandler ingredientsHandler) {
        this.ingredientsHandler = ingredientsHandler;
        routes = createRouterFunction();
    }

    private RouterFunction createRouterFunction() {
        return RouterFunctions.route(
                GET("/ingredients").and(accept(MediaType.APPLICATION_JSON_UTF8)), this::getAllIngredients)
                .andRoute(
                        GET("/ingredients/{id}").and(accept(MediaType.APPLICATION_JSON_UTF8)), this::getIngredientById
                );

    }

    private Mono<ServerResponse> getAllIngredients(ServerRequest request) {
        return ingredientsHandler.getAllIngredients().collectList()
                .flatMap(this::toOkJsonResponse);
    }

    private Mono<ServerResponse> getIngredientById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return ingredientsHandler.getIngredient(id)
                .flatMap(this::toOkJsonResponse)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private <T> Mono<ServerResponse> toOkJsonResponse(T data) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(data));
    }

    public RouterFunction getRoutes() {
        return routes;
    }
}
