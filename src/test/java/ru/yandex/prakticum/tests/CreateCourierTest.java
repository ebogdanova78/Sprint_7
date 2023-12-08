package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.is;
import static ru.yandex.prakticum.steps.CourierSteps.createCourier;
import static ru.yandex.prakticum.steps.CourierSteps.deleteCourier;
import ru.yandex.prakticum.Courier;

public class CreateCourierTest {

    public String TEST_LOGIN = "Test_Login";
    public String TEST_PASSWORD = "Test_Password";
    public String TEST_FIRST_NAME = "Test_FirstName";
    public String TEST_PASSWORD_2 = "Test_Password_2";
    public String TEST_FIRST_NAME_2 = "Test_FirstName_2";


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    private final Courier courier = new Courier(TEST_LOGIN, TEST_PASSWORD, TEST_FIRST_NAME);

    @Test
    @DisplayName("Courier creation")
    @Description("Positive test")
    public void checkCourierCreation() {

        createCourier(courier)
                .then()
                .assertThat()
                .statusCode(HTTP_CREATED)
                .and()
                .assertThat()
                .body("ok", is(true))
                .log().all();

        deleteCourier(TEST_LOGIN, TEST_PASSWORD);
    }

    @Test
    @DisplayName("Сreating two identical couriers")
    @Description("Attempt to create two identical couriers. Negative test - HTTP 409")
    public void creationTwoIdenticalCouriers() {
        createCourier(TEST_LOGIN, TEST_PASSWORD, TEST_FIRST_NAME)
                .then()
                .assertThat()
                .statusCode(HTTP_CREATED)
                .and()
                .assertThat()
                .body("ok", is(true));
        createCourier(TEST_LOGIN, TEST_PASSWORD_2, TEST_FIRST_NAME_2)
                .then()
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .and()
                .assertThat()
                .body("message", is("Этот логин уже используется. Попробуйте другой."))
                .log().all();
    }

    @Test
    @DisplayName("Сreating a courier without a login")
    @Description("Attempt to create a courier without a login. Negative test - HTTP 400")
    public void creationWithoutLoginCourier() {
        createCourier(null, TEST_PASSWORD, TEST_FIRST_NAME)
                .then()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", is("Недостаточно данных для создания учетной записи"))
                .log().all();
    }

    @Test
    @DisplayName("Сreating a courier without a password")
    @Description("Attempt to create a courier without a password. Negative test - HTTP 400")
    public void creationWithoutPasswordCourier() {
        createCourier(TEST_LOGIN, null, TEST_FIRST_NAME)
                .then()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", is("Недостаточно данных для создания учетной записи"))
                .log().all();
    }

    @After
    public void clearData() {
        deleteCourier(TEST_LOGIN,TEST_PASSWORD);
    }
}
