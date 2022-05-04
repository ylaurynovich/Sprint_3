package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static ru.praktikum.scooter.BaseClient.STATUS_CODE_WRONG;

public class OrderTest {

    private Order order;
    private OrderClient orderClient;
    private int orderTrack;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = Order.getRandom();
    }

    @After
    public void tearDown() {
        orderClient.cancel(orderTrack);
    }

    @Test
    @DisplayName("Создание заказа: возвращается верный статус код")
    public void createRequestReturnsSuccessStatusCode() {
        ValidatableResponse response = orderClient.create(order);
        int statusCode = response.extract().statusCode();
        assertEquals(STATUS_CODE_WRONG, 201, statusCode);
        orderTrack = response.extract().path("track");
    }

    @Test
    @DisplayName("Создание заказа: тело ответа содержит track.")
    public void createRequestReturnsId() {
        ValidatableResponse response = orderClient.create(order);
        orderTrack = response.extract().path("track");
        assertNotEquals("Id is not returned.", 0, orderTrack);
    }

}
