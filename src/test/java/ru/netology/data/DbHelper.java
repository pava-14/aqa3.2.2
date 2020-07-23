package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class DbHelper {
    private DbHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getValidAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationInfo {
        String login;
        String code;
    }

    public static VerificationInfo getVerificationCode(AuthInfo authInfo) throws SQLException {
        val codeSQL = "SELECT auth_codes.code FROM users INNER JOIN auth_codes ON auth_codes.user_id "
                + "= users.id WHERE users.login = ? ORDER by auth_codes.created DESC LIMIT 1;";
        val runner = new QueryRunner();
        String code;
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )
        ) {
            code = runner.query(conn, codeSQL, new ScalarHandler<>(), authInfo.getLogin());
        }
        return new VerificationInfo(authInfo.getLogin(), code);
    }

    public static VerificationInfo generateVerificationCode(AuthInfo authInfo) {
        Faker faker = new Faker(new Locale("ru"));
        return new VerificationInfo(
                authInfo.getLogin(),
                faker.number().digits(5)
        );
    }

    public static AuthInfo generateAuthInfo() {
        Faker faker = new Faker(new Locale("ru"));
        return new AuthInfo(
                faker.name().username().replace(".", ""),
                faker.internet().password()
        );
    }

    public static void ClearAuthCodesTable() throws SQLException {
        val deleteAuthCodeSQL = " DELETE FROM auth_codes;";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )
        ) {
            runner.execute(conn, deleteAuthCodeSQL, new ScalarHandler<>());
        }
    }
}
