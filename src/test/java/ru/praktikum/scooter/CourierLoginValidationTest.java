package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static ru.praktikum.scooter.BaseClient.STATUS_CODE_WRONG;

@RunWith(Parameterized.class)
public class CourierLoginValidationTest {

    private final Courier courier;
    private final int expectedStatusCode;
    private final String expectedErrorMessage;

    public CourierLoginValidationTest(Courier courier, int expectedStatusCode, String expectedErrorMessage) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters (name = "Логин курьера: {0} отсутствие пароля {1} отсутствие логина")
    public static Object[][] getTestData() {
        return new Object[][]{
                {Courier.getLoginOnly(), 400, "Недостаточно данных для входа"},
                {Courier.getPasswordOnly(), 400, "Недостаточно данных для входа"}
        };
    }

    @Test
    @DisplayName("Логин курьера: если какого-то поля нет, запрос возвращает ошибку")
    public void invalidLoginRequest() {
        ValidatableResponse response = new CourierClient().login(CourierAuth.from(courier));
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");
        assertEquals(STATUS_CODE_WRONG, statusCode, expectedStatusCode);
        assertEquals("Error message is wrong.", errorMessage, expectedErrorMessage);
    }

}
