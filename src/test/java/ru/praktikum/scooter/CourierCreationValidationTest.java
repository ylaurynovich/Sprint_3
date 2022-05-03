package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static ru.praktikum.scooter.BaseClient.STATUS_CODE_WRONG;

@RunWith(Parameterized.class)
public class CourierCreationValidationTest {

    private final Courier courier;
    private final int expectedStatusCode;
    private final String expectedErrorMessage;

    public CourierCreationValidationTest(Courier courier, int expectedStatusCode, String expectedErrorMessage) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters(name = "Создание курьера: {0} валидные данные {1} только логин {3} только пароль.")
    public static Object[][] getTestData() {
        return new Object[][]{
                {Courier.getLoginAndPassword(), 201, null},
                {Courier.getLoginOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getPasswordOnly(), 400, "Недостаточно данных для создания учетной записи"}

        };
    }

    @Test
    @DisplayName("Создание курьера")
    public void invalidCourierCreateRequest() {
        ValidatableResponse response = new CourierClient().create(courier);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");
        assertEquals(STATUS_CODE_WRONG, statusCode, expectedStatusCode);
        assertEquals("Message is wrong.", errorMessage, expectedErrorMessage);
    }

}
