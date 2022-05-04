package ru.praktikum.scooter;

public class CourierAuth {

    public final String login;
    public final String password;

    public CourierAuth(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierAuth from(Courier courier) {
        return new CourierAuth(courier.login, courier.password);
    }
}
