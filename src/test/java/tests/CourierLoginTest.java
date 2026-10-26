package tests;

import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestDataFactory;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

@DisplayName("Авторизация курьера")
class CourierLoginTest extends BaseCourierTest {
    private static final String LOGIN_DATA_REQUIRED = "Недостаточно данных для входа";
    private static final String ACCOUNT_NOT_FOUND = "Учетная запись не найдена";

    @Test
    @DisplayName("Авторизация с верными данными возвращает числовой id курьера")
    void testLoginCourier() {
        Courier courier = createCourier();

        Response response = courierSteps.loginCourier(TestDataFactory.credentialsFor(courier));

        response.then()
                .statusCode(SC_OK)
                .body("id", instanceOf(Integer.class));
    }

    @Test
    @DisplayName("Авторизация без поля login возвращает ошибку 400")
    void testLoginWithoutLogin() {
        Courier courier = createCourier();
        CourierCredentials credentials = new CourierCredentials(null, courier.getPassword());

        Response response = courierSteps.loginCourier(credentials);

        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("code", equalTo(SC_BAD_REQUEST))
                .body("message", equalTo(LOGIN_DATA_REQUIRED));
    }

    @Test
    @DisplayName("Авторизация без поля password возвращает ошибку 400")
    void testLoginWithoutPassword() {
        Courier courier = createCourier();
        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), null);

        Response response = courierSteps.loginCourier(credentials);

        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("code", equalTo(SC_BAD_REQUEST))
                .body("message", equalTo(LOGIN_DATA_REQUIRED));
    }

    @Test
    @DisplayName("Авторизация с неправильным паролем возвращает ошибку 404")
    void testLoginWithWrongPassword() {
        Courier courier = createCourier();
        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), TestDataFactory.wrongPassword());

        Response response = courierSteps.loginCourier(credentials);

        response.then()
                .statusCode(SC_NOT_FOUND)
                .body("code", equalTo(SC_NOT_FOUND))
                .body("message", equalTo(ACCOUNT_NOT_FOUND));
    }

    @Test
    @DisplayName("Авторизация с несуществующим логином возвращает ошибку 404")
    void testLoginWithNonexistentLogin() {
        Courier nonexistentCourier = TestDataFactory.newCourier();

        Response response = courierSteps.loginCourier(TestDataFactory.credentialsFor(nonexistentCourier));

        response.then()
                .statusCode(SC_NOT_FOUND)
                .body("code", equalTo(SC_NOT_FOUND))
                .body("message", equalTo(ACCOUNT_NOT_FOUND));
    }
}
