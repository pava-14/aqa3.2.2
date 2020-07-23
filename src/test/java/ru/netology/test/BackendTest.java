package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DbHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class BackendTest {

    @AfterAll
    public static void PostConditions() throws SQLException {
        DbHelper.ClearAuthCodesTable();
    }

    @Test
    public void openDashboardWithValidAuthInfo() {
        val loginPage = new LoginPage();
        val authInfo = DbHelper.getValidAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        DbHelper.VerificationInfo verificationInfo = null;
        try {
            verificationInfo = DbHelper.getVerificationCode(authInfo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DashboardPage page = verificationPage.validVerify(verificationInfo);
    }

    @Test
    public void shouldNotBlockedWithInvalidPasswordAfterThreeAttempts() {
        val loginPage = new LoginPage();
        loginPage.invalidLogin();
    }

    public void shouldShowCards () {

    }

    public void shouldMoneyTransferCardByCard () {

    }
}
