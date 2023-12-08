package ru.yandex.prakticum.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import ru.yandex.prakticum.Courier;
import static io.restassured.RestAssured.given;


import io.qameta.allure.Step;

public class CourierSteps {

    public static final String COURIER = "/api/v1/courier";
    public static final String LOGIN = "/api/v1/courier/login";
    public static final String COURIER_ID = "/api/v1/courier/%s";

    @Step("Create courier")
    public static Response createCourier(String login, String password, String firstName) {

        var courier = new Courier(login, password, firstName);
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER)
                .then()
                .extract().response();
    }

    @Step("Create courier")
    public static Response createCourier(Courier courier) {
        return   given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER)
                .then()
                .extract().response();

    }

    @Step("Login courier")
    public static Response loginCourier(String login, String password) {
        var courier = new Courier(login, password);
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(LOGIN)
                .then()
                .extract().response();
    }

    @Step("Login courier")
    public static Response loginCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(LOGIN)
                .then()
                .extract().response();
    }
    @Step("Delete courier")
    public static Response deleteCourier(String login, String password) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(String.format(COURIER_ID, loginCourier(login, password).jsonPath().getString("id")))
                .then()
                .extract().response();
    }

    @Step("Delete courier by id")
    public static Response deleteCourierById(String id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(String.format(COURIER_ID, id))
                .then()
                .extract().response();
    }
}
