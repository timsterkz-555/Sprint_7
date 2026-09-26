package tests;

import io.restassured.response.Response;
import model.Courier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestDataFactory;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Создание курьера")
class CourierCreateTest extends BaseCourierTest {
    private static final String COURIER_DATA_REQUIRED = "Недостаточно данных для создания учетной записи";

    @Test
    @DisplayName("Курьер с уникальным логином успешно создаётся")
    void testCreateCourier() {
        Courier courier = TestDataFactory.newCourier();

        Response response = createCourier(courier);

        response.then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Повторное создание курьера с тем же логином возвращает ошибку 409")
    void testCreateDuplicateCourier() {
        Courier courier = createCourier();

        Response response = courierSteps.createCourier(courier);

        response.then()
                .statusCode(SC_CONFLICT)
                .body("code", equalTo(SC_CONFLICT))
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без поля login возвращает ошибку 400")
    void testCreateCourierWithoutLogin() {
        Courier courier = TestDataFactory.newCourier();
        Courier withoutLogin = new Courier(null, courier.getPassword(), courier.getFirstName());

        Response response = createCourier(withoutLogin);

        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("code", equalTo(SC_BAD_REQUEST))
                .body("message", equalTo(COURIER_DATA_REQUIRED));
    }

    @Test
    @DisplayName("Создание курьера без поля password возвращает ошибку 400")
    void testCreateCourierWithoutPassword() {
        Courier courier = TestDataFactory.newCourier();
        Courier withoutPassword = new Courier(courier.getLogin(), null, courier.getFirstName());

        Response response = createCourier(withoutPassword);

        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("code", equalTo(SC_BAD_REQUEST))
                .body("message", equalTo(COURIER_DATA_REQUIRED));
    }
}
