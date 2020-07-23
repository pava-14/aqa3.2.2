package ru.netology.page;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.netology.data.DbHelper;

import static com.codeborne.selenide.Selenide.page;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginPage {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .addFilter(new ResponseLoggingFilter())
            .log(LogDetail.ALL)
            .build();

    public VerificationPage validLogin(DbHelper.AuthInfo info) {
        given()
                .spec(requestSpec)
                .body(info)
                .when()
                .post("/api/auth") // на какой путь, относительно BaseUri отправляем запрос
                .then()
                .statusCode(200); // Login OK
        return page(VerificationPage.class);
    }

    public void invalidLogin() {
        for (int i = 0; i < 5; i++) {
            String code =
                    given()
                    .spec(requestSpec)
                    .body(DbHelper.generateAuthInfo())
                    .when()
                    .post("/api/auth") // на какой путь, относительно BaseUri отправляем запрос
                    .then()
                    .statusCode(400)
                    .extract()
                    .path("code");
            assertThat(code, equalTo("AUTH_INVALID"));
        }
    }
}
