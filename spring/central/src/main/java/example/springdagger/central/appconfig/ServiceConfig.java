package example.springdagger.central.appconfig;

import example.springdagger.central.data.IngredientsDAO;
import example.springdagger.central.data.PizzaDAO;
import example.springdagger.central.services.PizzaCatalogService;
import example.springdagger.central.services.PizzaOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    @Autowired
    public PizzaCatalogService pizzaCatalogService(IngredientsDAO ingredientsDAO, PizzaDAO pizzaDAO) {
        return new PizzaCatalogService(ingredientsDAO, pizzaDAO);
    }

    @Bean
    public PizzaOrderService pizzaOrderService() {
        return new PizzaOrderService();
    }
}
