package example.springdagger.central.routes;

import example.springdagger.central.data.transfer.dto.NewPizzaOrder;
import example.springdagger.central.routes.handlers.IngredientsHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

public class PizzaCatalogRoutes {
    private final IngredientsHandler ingredientsHandler;
    private final RouterFunction routes;

    public PizzaCatalogRoutes(IngredientsHandler ingredientsHandler) {
        this.ingredientsHandler = ingredientsHandler;
        routes = createRouterFunction();
    }

    private RouterFunction createRouterFunction() {
        return RouterFunctions.route(
                GET("/ingredients").and(acceptJson()), this::getAllIngredients)
                .andRoute(
                        GET("/ingredients/{id}").and(acceptJson()), this::getIngredientById
                )
                .andRoute(
                        GET("/pizza").and(acceptJson()), this::getPizzas
                )
                .andRoute(
                        POST("/order").and(acceptJson()).and(contentType(MediaType.APPLICATION_JSON_UTF8)), this::orderPizza
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

    private Mono<ServerResponse> getPizzas(ServerRequest request) {
        Integer amountOfPizzas = request.queryParam("amount").map(Integer::valueOf).orElse(10);
        return ingredientsHandler.getPizzas(amountOfPizzas).collectList()
                .flatMap(this::toOkJsonResponse);
    }

    private Mono<ServerResponse> orderPizza(ServerRequest request) {
        Mono<NewPizzaOrder> newPizzaOrder = request.bodyToMono(NewPizzaOrder.class);
        return ingredientsHandler.orderPizza(newPizzaOrder)
                .then(ServerResponse.status(HttpStatus.CREATED).build());
    }

    private RequestPredicate acceptJson() {
        return accept(MediaType.APPLICATION_JSON_UTF8);
    }

    private <T> Mono<ServerResponse> toOkJsonResponse(T data) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(data));
    }

    public RouterFunction getRoutes() {
        return routes;
    }
}
