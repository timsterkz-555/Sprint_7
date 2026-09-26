package utils;

import model.Courier;
import model.CourierCredentials;
import model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public final class TestDataFactory {

    private static final String COURIER_PASSWORD = "Sprint7-password";
    private static final String COURIER_FIRST_NAME = "Тестовый курьер";

    private TestDataFactory() {
    }

    public static Courier newCourier() {
        return new Courier(uniqueLogin(), COURIER_PASSWORD, COURIER_FIRST_NAME);
    }

    public static CourierCredentials credentialsFor(Courier courier) {
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    public static String uniqueLogin() {
        return "sprint7_" + UUID.randomUUID();
    }

    public static String wrongPassword() {
        return COURIER_PASSWORD + "-incorrect";
    }

    public static Order newOrder(List<String> color) {
        return new Order(
                "Тестовый",
                "Заказчик",
                "Москва, улица Тестовая, дом 7",
                4,
                "+7 800 355 35 35",
                5,
                LocalDate.now().plusDays(7).toString(),
                "Sprint_7 API test " + UUID.randomUUID(),
                color
        );
    }
}
