package ru.yandex.prakticum.steps;

import io.qameta.allure.Step;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import ru.yandex.prakticum.Courier;
import ru.yandex.prakticum.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    public static final String ORDERS_LIST = "/api/v1/courier/%s/orders";

    public static final String NEW_ORDER = "/api/v1/orders";

    public static final String ACCEPT_ORDER = "/api/v1/orders/accept/%s";

    public static final String CANCEL_ORDER = "/api/v1/orders/cancel";

    public static final String ORDERS = "/api/v1/orders";

    @Step("Create order")
    public static Response createOrder(Order order) {
        return given()
                .body(order)
                .when()
                .post(NEW_ORDER)
                .then()
                .extract()
                .response();
    }

    @Step("Accept order")
    public static Response acceptOrder(Order order, Courier courier) {
        return given()
                .queryParam("courierId", courier.getId())
                .when()
                .put(String.format(ACCEPT_ORDER, order.getTrack()))
                .then()
                .extract()
                .response();
    }

    @Step("Accept order2")
    public static Response acceptOrder2(Order order, Courier courier) {
        return given()
                .when()
                .put(String.format(ACCEPT_ORDER, order.getTrack()))
                .then()
                .extract()
                .response();
    }
    @Step("Cancel order")
    public static void cancelOrder(String track) {
        given()
                .queryParam("track", track)
                .when()
                .put(CANCEL_ORDER);
    }

    @Step("Get orders list")
    public static Response getOrders() {
        return given()
                .when()
                .get(ORDERS)
                .then()
                .extract()
                .response();
    }
}