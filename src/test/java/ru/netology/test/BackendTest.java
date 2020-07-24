package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DbHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BackendTest {
    private final String from = "5559 0000 0000 0002";
    private final String to = "5559 0000 0000 0008";
    private final int amount = 5000;

    @AfterAll
    public static void postConditions() {
        DbHelper.clearAuthCodesTable();
    }

    private DashboardPage openDashboard() {
        val loginPage = new LoginPage();
        val authInfo = DbHelper.getValidAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        return verificationPage.validVerify(DbHelper.getVerificationCode(authInfo));
    }

    @Test
    public void shouldLoginWithValidAuthInfo() {
        DashboardPage dashboardPage = openDashboard();
        assertThat(dashboardPage, is(notNullValue()));
    }

    @Test
    public void shouldShowCards() {
        DashboardPage dashboardPage = openDashboard();
        int expected = 2;
        assertEquals(expected, dashboardPage.ShowCards());
    }

    @Test
    public void shouldMoneyTransferCardByCard() {
        DashboardPage dashboardPage = openDashboard();
        dashboardPage.MoneyTransfer(
                new DbHelper.TransferInfo(from, to, String.valueOf(amount)));
    }

    @Test
    public void shouldNotBlockedWithInvalidPasswordAfterThreeAttempts() {
        val loginPage = new LoginPage();
        loginPage.invalidLogin();
    }
}
