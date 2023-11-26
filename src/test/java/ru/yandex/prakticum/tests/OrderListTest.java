package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.prakticum.Courier;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.prakticum.steps.OrderSteps.getOrders;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }


    @Test
    @DisplayName("Get orders list")
    @Description("Basic test for getting orders list")
    public void checkOrderList() {
        getOrders()
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .assertThat()
                .body(notNullValue());
    }
}
