package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class OrderListTest {

    private OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Список заказов: в тело заказа возвращается список заказов")
    public void orderList() {
        ValidatableResponse response = orderClient.getOrderList();
        response.assertThat().body("orders.size()", is(not(0)));
    }

}