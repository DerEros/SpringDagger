package example.springdagger.central;

import example.springdagger.central.routes.PizzaCatalogRoutes;
import example.springdagger.central.routes.handlers.IngredientsHandler;
import example.springdagger.central.services.PizzaCatalogService;
import example.springdagger.central.services.PizzaOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

@Configuration
public class RoutesConfig {
    @Bean
    @Autowired
    public RouterFunction pizzaCatalogRoutes(PizzaCatalogRoutes routeFactory) {
        return routeFactory.getRoutes();
    }

    @Bean
    @Autowired
    public PizzaCatalogRoutes pizzaCatalogRouteFactory(IngredientsHandler ingredientsHandler) {
        return new PizzaCatalogRoutes(ingredientsHandler);
    }

    @Bean
    @Autowired
    public IngredientsHandler ingredientsHandler(PizzaCatalogService pizzaCatalogService,
                                                 PizzaOrderService pizzaOrderService) {
        return new IngredientsHandler(pizzaCatalogService, pizzaOrderService);
    }
}
