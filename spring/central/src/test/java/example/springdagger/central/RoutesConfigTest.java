package example.springdagger.central;

import example.springdagger.central.appconfig.RoutesConfig;
import example.springdagger.central.services.PizzaCatalogService;
import example.springdagger.central.services.PizzaOrderService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.server.RouterFunction;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RoutesConfig.class})
@Tag("WebClient")
class RoutesConfigTest {
    @Inject
    private List<RouterFunction> routerFunctions;

    @MockBean
    private PizzaCatalogService pizzaCatalogServiceMock;

    @MockBean
    private PizzaOrderService pizzaOrderServiceMock;

    @Test
    void testContextLoadsProperly() {
        assertThat(routerFunctions).isNotEmpty();
    }

}