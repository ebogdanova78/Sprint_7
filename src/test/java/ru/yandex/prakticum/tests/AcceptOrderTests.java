/*package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.prakticum.Courier;
import ru.yandex.prakticum.Order;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.prakticum.steps.CourierSteps.*;
import static ru.yandex.prakticum.steps.OrderSteps.*;
import static ru.yandex.prakticum.tests.CreateOrderTests.*;


public class AcceptOrderTests {

    public String TEST_LOGIN = "Test_Login";
    public String TEST_PASSWORD = "Test_Password";
    public String TEST_FIRST_NAME = "Test_FirstName";

    private final Order order = new Order(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS, TEST_METRO_STATION, TEST_PHONE, TEST_RENT_TIME, TEST_DELIVERY_DATE, TEST_COMMENT, TEST_COLORS);
    private final Courier courier = new Courier(TEST_LOGIN, TEST_PASSWORD, TEST_FIRST_NAME);

    @Before

    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    @Test
    @DisplayName("Accept order")
    @Description("Positive order accept")
    public void checkOrderAccept() {
        createCourier(courier);
        courier.setId(loginCourier(courier.getLogin(), courier.getPassword()).jsonPath().getString("id"));
        order.setTrack(createOrder(order).jsonPath().getString("track"));

        acceptOrder(order, courier)
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .assertThat()
                .body("ok", is(true));

        cancelOrder(order.getTrack());
        deleteCourier(TEST_LOGIN, TEST_PASSWORD);

    }

    @Test
    @DisplayName("Accept order without courier Id")
    @Description("Attempt to accept order without courier Id. Negative test - HTTP 400")
    public void checkOrderAcceptWithoutCourierId () {
        order.setTrack(createOrder(order).jsonPath().getString("track"));
        acceptOrder2(order, courier)
                .then()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для поиска"))
                .log().all();
        cancelOrder(order.getTrack());
    }

}*/
