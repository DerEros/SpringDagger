package example.springdagger.central.appconfig;

import example.springdagger.central.data.IngredientsDAO;
import example.springdagger.central.data.PizzaDAO;
import example.springdagger.central.data.repos.IngredientsRepo;
import example.springdagger.central.data.repos.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DAOConfig {
    @Bean
    @Autowired
    public PizzaDAO pizzaDAO(PizzaRepo pizzaRepo) {
        return new PizzaDAO(pizzaRepo);
    }

    @Bean
    @Autowired
    public IngredientsDAO ingredientsDAO(IngredientsRepo ingredientsRepo) {
        return new IngredientsDAO(ingredientsRepo);
    }
}
