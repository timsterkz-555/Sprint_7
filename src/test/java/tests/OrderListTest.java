package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.OrderSteps;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.instanceOf;

@DisplayName("Получение списка заказов")
class OrderListTest {
    private final OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Список заказов возвращается в поле orders с HTTP-статусом 200")
    void testGetOrders() {
        Response response = orderSteps.getOrders();

        response.then()
                .statusCode(SC_OK)
                .body("orders", instanceOf(List.class));
    }
}
