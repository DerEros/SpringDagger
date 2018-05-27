package example.springdagger.decentral.data;

import example.springdagger.decentral.model.Pizza;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PizzaDAO.class, RepositoryConfig.class})
@DataJpaTest
@EntityScan(basePackageClasses = {Pizza.class})
@Tag("DBTest")
class PizzaDAOTest {
    @Test
    @Sql({"Pizza.sql"})
    void testLoadDDL() {
    }
}