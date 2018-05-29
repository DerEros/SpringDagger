package example.springdagger.central;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Tag("Integration")
class PizzaCatalogTest {
    @Test
    void testFullAppStartup() {
        PizzaCatalog.main(new String[]{});
    }
}