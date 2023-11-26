package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;
import static ru.yandex.prakticum.steps.CourierSteps.*;

public class LoginCourierTest {
    public String TEST_LOGIN = "Test_Login";
    public String TEST_PASSWORD = "Test_Password";

    public String TEST_FIRST_NAME = "Test_FirstName";


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    @Test
    @DisplayName("Successful login")
    @Description("Successful attempt to login.")
    public void checkSuccessfulLogin() {
        createCourier(TEST_LOGIN, TEST_PASSWORD, TEST_FIRST_NAME);
        loginCourier(TEST_LOGIN, TEST_PASSWORD)
                .then()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(HTTP_OK)
                .log().all();
    }

    @Test
    @DisplayName("Empty login field")
    @Description("Attempt to login without login field")
    public void checkEmptyLogin() {
        loginCourier(null, TEST_PASSWORD)
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(HTTP_BAD_REQUEST)
                .log().all();

    }

    @Test
    @DisplayName("Empty password field")
    @Description("Attempt to login without password field")
    public void checkEmptyPassword() {
        loginCourier(TEST_LOGIN, null)
                .then()
                .assertThat()
                .statusCode(504)
                .body(equalTo("Service unavailable"))
                .log().all();

    }

    @Test
    @DisplayName("Non-existent login")
    @Description("Attempt to login using non-existent login")
    public void checkNonexistentLogin() {
        loginCourier(TEST_LOGIN + "new", TEST_PASSWORD)
                .then()
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"))
                .log().all();
    }


    @Test
    @DisplayName("Non-existent courier")
    @Description("Attempt to login as non-existent courier")
    public void checkNonexistentCourier() {
        loginCourier(TEST_LOGIN + "new", TEST_PASSWORD + "new")
                .then()
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"))
                .log().all();

    }

    @Test
    @DisplayName("Non-existent password")
    @Description("Attempt to login using non-existent password")
    public void checkNonexistentPassword() {
        loginCourier(TEST_LOGIN , TEST_PASSWORD + "new")
                .then()
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"))
                .log().all();

    }
}
