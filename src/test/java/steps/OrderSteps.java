package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;
import utils.ApiConfig;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    private static final String ORDERS_PATH = "/api/v1/orders";
    private static final int ORDERS_PAGE_SIZE = 10;

    @Step("Создать заказ")
    public Response createOrder(Order order) {
        return given()
                .spec(ApiConfig.request())
                .body(order)
                .post(ORDERS_PATH);
    }

    @Step("Отменить заказ с track: {track}")
    public Response cancelOrder(int track) {
        return given()
                .spec(ApiConfig.request())
                .queryParam("track", track)
                .put(ORDERS_PATH + "/cancel");
    }

    @Step("Получить список заказов")
    public Response getOrders() {
        return given()
                .spec(ApiConfig.request())
                .queryParam("limit", ORDERS_PAGE_SIZE)
                .get(ORDERS_PATH);
    }
}
