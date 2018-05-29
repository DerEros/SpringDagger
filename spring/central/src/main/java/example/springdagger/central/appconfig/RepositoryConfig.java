package example.springdagger.central.appconfig;

import example.springdagger.central.data.repos.IngredientsRepo;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = IngredientsRepo.class)
public class RepositoryConfig {
}
