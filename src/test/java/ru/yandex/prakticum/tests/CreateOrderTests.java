package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.prakticum.Order;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.yandex.prakticum.steps.OrderSteps.cancelOrder;
import static ru.yandex.prakticum.steps.OrderSteps.createOrder;


@RunWith(Parameterized.class)
public class CreateOrderTests  {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    public static final String TEST_FIRST_NAME = "Test_FirstName";
    public static final String TEST_LAST_NAME = "Test_LastName";
    public static final String TEST_ADDRESS = "Test_Address";
    public static final String TEST_METRO_STATION = "Беговая";
    public static final String TEST_PHONE = "+123456789";
    public static final int TEST_RENT_TIME = 10;
    public static final String TEST_DELIVERY_DATE = LocalDate.now().plusDays(1).toString();
    public static final String TEST_COMMENT = "Test_Comment";
    public static final List<String> TEST_BLACK = List.of("BLACK");

    public static final List<String> TEST_COLORS = Arrays.asList("BLACK", "GREY");
    private String track;

    private static final List<Order> ORDERS = List
            .of(new Order(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS, TEST_METRO_STATION, TEST_PHONE, TEST_RENT_TIME, TEST_DELIVERY_DATE, TEST_COMMENT, TEST_BLACK),
                    new Order(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS, TEST_METRO_STATION, TEST_PHONE, TEST_RENT_TIME, TEST_DELIVERY_DATE, TEST_COMMENT, TEST_COLORS),
                    new Order(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS, TEST_METRO_STATION, TEST_PHONE, TEST_RENT_TIME, TEST_DELIVERY_DATE, TEST_COMMENT));
    private final Order order;

    public CreateOrderTests(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static List<Order> getOrderCreationTestData() {
        return ORDERS;
    }

    @Test
    @DisplayName("Create order")
    @Description("Positive order creation")
    public void checkOrderCreation() {
        var response = createOrder(order);
        response.then()
                .assertThat()
                .body("track", notNullValue())
                .and()
                .statusCode(HTTP_CREATED)
                .log().all();
        order.setTrack(response.jsonPath().getString("track"));
    }

    @After
    public void clearData() {
        cancelOrder(order.getTrack());
    }

}
