package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;
import utils.ApiConfig;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    private static final String COURIER_PATH = "/api/v1/courier";

    @Step("Создать курьера")
    public Response createCourier(Courier courier) {
        return given()
                .spec(ApiConfig.request())
                .body(courier)
                .post(COURIER_PATH);
    }

    @Step("Авторизоваться курьером")
    public Response loginCourier(CourierCredentials credentials) {
        return given()
                .spec(ApiConfig.request())
                .body(credentials)
                .post(COURIER_PATH + "/login");
    }

    @Step("Удалить курьера с id: {courierId}")
    public Response deleteCourier(int courierId) {
        return given()
                .spec(ApiConfig.request())
                .pathParam("courierId", courierId)
                .delete(COURIER_PATH + "/{courierId}");
    }
}
