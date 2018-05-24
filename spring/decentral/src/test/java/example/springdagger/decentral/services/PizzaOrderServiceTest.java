package example.springdagger.decentral.services;

import example.springdagger.decentral.data.transfer.dto.NewPizzaOrder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PizzaOrderService.class})
@Tag("UnitTest")
class PizzaOrderServiceTest {

    @Inject
    private PizzaOrderService pizzaOrderService;

    @Test
    void testOrderPizza() {
        NewPizzaOrder.SimplifiedPizza pizza = new NewPizzaOrder.SimplifiedPizza(Arrays.asList(1L, 3L, 5L));
        NewPizzaOrder.PizzaAndAmount pizzaAndAmount = new NewPizzaOrder.PizzaAndAmount(pizza, 3L);
        NewPizzaOrder order = new NewPizzaOrder(Collections.singletonList(pizzaAndAmount));

        StepVerifier.create(pizzaOrderService.orderPizza(Mono.just(order))).verifyComplete();
    }

    @Test
    void testInvalidPizzaOrder() {
        StepVerifier.create(pizzaOrderService.orderPizza(Mono.empty())).verifyError();
    }
}