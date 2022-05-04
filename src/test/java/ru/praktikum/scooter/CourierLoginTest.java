package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static ru.praktikum.scooter.BaseClient.STATUS_CODE_WRONG;

public class CourierLoginTest {

    private Courier courier;
    private CourierClient courierService;
    private int courierId;

    @Before
    public void setUp() {
        courierService = new CourierClient();
        courier = Courier.getRandom();
        courierService.create(courier);
        courierId = courierService.login(CourierAuth.from(courier)).extract().path("id");
    }

    @After
    public void tearDown() {
        courierService.delete(courierId);
    }

    @Test
    @DisplayName("Логин курьера: курьер может авторизоваться")
    public void loginRequestReturnsSuccess() {
        ValidatableResponse response = courierService.login(CourierAuth.from(courier));
        int statusCode = response.extract().statusCode();
        assertEquals(STATUS_CODE_WRONG, 200, statusCode);
    }
    @Test
    @DisplayName("Логин курьера: успешный логин возращает id")
    public void loginRequestReturnsId() {
        ValidatableResponse response = courierService.login(CourierAuth.from(courier));
        int id = response.extract().path("id");
        assertNotEquals("Id is not returned.", 0, id);
    }
    @Test
    @DisplayName("Логин курьера: система вернёт ошибку, если неправильно указать логин")
    public void errorOnInvalidLogin() {
        courier.login = RandomStringUtils.randomAlphabetic(10);
        ValidatableResponse response = courierService.login(CourierAuth.from(courier));
        int statusCode = response.extract().statusCode();
        assertEquals(STATUS_CODE_WRONG, 404, statusCode);
    }

    @Test
    @DisplayName("система вернёт ошибку, если неправильно указать пароль")
    public void errorOnWrongPassword() {
        courier.password = RandomStringUtils.randomAlphabetic(10);
        ValidatableResponse response = courierService.login(CourierAuth.from(courier));
        int statusCode = response.extract().statusCode();
        assertEquals(STATUS_CODE_WRONG, 404, statusCode);
    }

}
