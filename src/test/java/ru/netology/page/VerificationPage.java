package ru.netology.page;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.data.DbHelper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class VerificationPage {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .addFilter(new ResponseLoggingFilter())
            .log(LogDetail.ALL)
            .build()
            .filter(new AllureRestAssured());

    public VerificationPage() {
    }

    public DashboardPage validVerify(DbHelper.VerificationInfo verificationInfo) {
        String token =
                given()
                        .spec(requestSpec)
                        .body(verificationInfo)
                        .when()
                        .post("api/auth/verification")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");
        assertThat(token, is(notNullValue()));
        return new DashboardPage(token);
    }
}
