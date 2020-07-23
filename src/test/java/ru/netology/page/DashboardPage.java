package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private String token;

    public DashboardPage(String token) {
        this.token = token;
    }
}
