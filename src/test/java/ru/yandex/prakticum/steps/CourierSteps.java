package ru.yandex.prakticum.steps;

import io.restassured.response.Response;
import ru.yandex.prakticum.Courier;

import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class CourierSteps {

    public static final String COURIER = "/api/v1/courier";
    private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";
    //private static final String COURIER = BASE_URL + "/courier";

    @Step("Create courier")
    public static Response createCourier(String login, String password, String firstName) {
        var courier = new Courier(login, password, firstName);
        return given()
                .body(courier)
                .when()
                .post(COURIER)
                .then()
                .extract().response();
    }




}
