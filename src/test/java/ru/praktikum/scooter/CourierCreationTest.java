package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static ru.praktikum.scooter.BaseClient.STATUS_CODE_WRONG;

public class CourierCreationTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Cоздание курьера: запрос возвращает правильный код ответа")
    public void createRequestReturnsSuccessStatusCode() {
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        courierId = courierClient.login(CourierAuth.from(courier)).extract().path("id");
        assertEquals(STATUS_CODE_WRONG, 201, statusCode);
    }

    @Test
    @DisplayName("Создание курьера: успешный запрос возвращает ok: true")
    public void createRequestReturnsSuccessMessage() {
        ValidatableResponse response = courierClient.create(courier);
        boolean isCreated = response.extract().path("ok");
        courierId = courierClient.login(CourierAuth.from(courier)).extract().path("id");
        assertTrue("Courier is not created.", isCreated);
    }

    @Test
    @DisplayName("Создание курьера: нельзя создать двух одинаковых курьеров")
    public void sameCourierCannotBeCreatedTwice() {
        courierClient.create(courier);
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");
        courierId = courierClient.login(CourierAuth.from(courier)).extract().path("id");
        assertEquals(STATUS_CODE_WRONG, 409, statusCode);
        assertEquals ("Error message is wrong.", "Этот логин уже используется. Попробуйте другой.", errorMessage);
    }


}