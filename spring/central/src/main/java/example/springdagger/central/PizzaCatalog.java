package example.springdagger.central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the pizza catalog service
 */
@SpringBootApplication
public class PizzaCatalog {

    public static void main(String[] args) {
        SpringApplication.run(PizzaCatalog.class, args);
    }
}
