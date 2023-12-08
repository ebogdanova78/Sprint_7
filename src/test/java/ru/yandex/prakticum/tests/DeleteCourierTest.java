package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.is;
import static ru.yandex.prakticum.steps.CourierSteps.*;

public class DeleteCourierTest {

    public String TEST_LOGIN = "Test_Login";
    public String TEST_PASSWORD = "Test_Password";

    public String TEST_FIRST_NAME = "Test_FirstName";

    public String INVALID_ID = "54321";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Courier delete")
    @Description("Successful delete")
    public void checkCourierDelete() {
        createCourier(TEST_LOGIN, TEST_PASSWORD, TEST_FIRST_NAME);
        deleteCourier(TEST_LOGIN, TEST_PASSWORD)
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .assertThat()
                .body("ok", is(true));

    }

    @Test
    @DisplayName("Courier delete without id")
    @Description("Attempt to delete courier without id")
    public void checkCourierDeleteWithoutId() {
                 given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .assertThat()
                .body("message", Matchers.is("Not Found."));
    }

    @Test
    @DisplayName("Courier delete with invalid id")
    @Description("Attempt to delete Courier with invalid id")
    public void checkCourierDeleteWithInvalidId() {
        deleteCourierById(INVALID_ID)
                .then()
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .assertThat()
                .body("message", Matchers.is("Курьера с таким id нет."));
    }
}