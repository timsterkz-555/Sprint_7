package tests;

import io.restassured.response.Response;
import model.Courier;
import org.junit.jupiter.api.AfterEach;
import steps.CourierSteps;
import utils.TestDataFactory;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

abstract class BaseCourierTest {
    protected final CourierSteps courierSteps = new CourierSteps();
    private Courier createdCourier;

    protected Response createCourier(Courier courier) {
        Response response = courierSteps.createCourier(courier);
        if (response.statusCode() == SC_CREATED) {
            createdCourier = courier;
        }
        return response;
    }

    protected Courier createCourier() {
        Courier courier = TestDataFactory.newCourier();
        createCourier(courier).then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
        return courier;
    }

    @AfterEach
    void deleteCreatedCourier() {
        if (createdCourier != null) {
            Response loginResponse = courierSteps.loginCourier(TestDataFactory.credentialsFor(createdCourier));
            loginResponse.then()
                    .statusCode(SC_OK)
                    .body("id", instanceOf(Integer.class));
            int courierId = loginResponse.jsonPath().getInt("id");
            courierSteps.deleteCourier(courierId).then()
                    .statusCode(SC_OK)
                    .body("ok", equalTo(true));
        }
    }
}
