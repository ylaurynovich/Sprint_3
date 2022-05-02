package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.praktikum.scooter.BaseClient.STATUS_CODE_WRONG;

@RunWith(Parameterized.class)
public class OrderColorTest {

    private final List<String> color;
    private final int expectedStatusCode;

    public OrderColorTest(List<String> color, int expectedStatusCode) {
        this.color = color;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {List.of("BLACK"), 201},
                {List.of("GREY"), 201},
                {List.of("BLACK", "GREY"), 201},
                {List.of(), 201}

        };
    }

    @Test
    @DisplayName("Создание заказа: 1 - указать цвет BLACK, 2 - указать цвет Gray, 3 - указать оба цвета, 4 - не указывать цвет")
    public void orderColor() {
        Order order = Order.getRandom().setColor(color);
        ValidatableResponse response = new OrderClient().create(order);
        int statusCode = response.extract().statusCode();
        assertEquals(STATUS_CODE_WRONG, statusCode, expectedStatusCode);
    }

}
