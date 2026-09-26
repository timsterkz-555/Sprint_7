package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class ApiConfig {

    private static final String BASE_URI = "https://qa-scooter.education-services.ru";

    private ApiConfig() {
    }

    public static RequestSpecification request() {
        HttpClientConfig httpClientConfig = HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 10_000)
                .setParam("http.socket.timeout", 90_000);

        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .setConfig(RestAssuredConfig.config().httpClient(httpClientConfig))
                .build();
    }
}
