package tests;

import io.restassured.response.Response;
import model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import steps.OrderSteps;
import utils.TestDataFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

@DisplayName("Создание заказа")
class OrderCreateTest {
    private final OrderSteps orderSteps = new OrderSteps();
    private Integer track;

    static Stream<Arguments> orderColors() {
        return Stream.of(
                Arguments.of("BLACK", Collections.singletonList("BLACK")),
                Arguments.of("GREY", Collections.singletonList("GREY")),
                Arguments.of("BLACK и GREY", Arrays.asList("BLACK", "GREY")),
                Arguments.of("поле color отсутствует", null));
    }

    @DisplayName("Заказ создаётся с выбранными цветами или без поля color")
    @ParameterizedTest(name = "Создание заказа: {0}")
    @MethodSource("orderColors")
    void testCreateOrder(String scenario, List<String> colors) {
        Order order = TestDataFactory.newOrder(colors);

        Response response = orderSteps.createOrder(order);
        track = response.jsonPath().get("track");

        response.then()
                .statusCode(SC_CREATED)
                .body("track", instanceOf(Integer.class));
    }

    @AfterEach
    void cancelCreatedOrder() {
        if (track != null) {
            orderSteps.cancelOrder(track).then()
                    .statusCode(SC_OK)
                    .body("ok", equalTo(true));
        }
    }
}
