package ru.netology.page;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.data.DbHelper;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardPage {
    private String token;
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .addFilter(new ResponseLoggingFilter())
            .log(LogDetail.ALL)
            .build();

    public DashboardPage(String token) {
        this.token = token;
    }

    public int ShowCards() {
        ArrayList<String> cardList =
                given()
                        .spec(requestSpec)
                        .header(
                                "Authorization",
                                "Bearer " + token)
                        .header("Content-Type",
                                "application/json")
                        .when()
                        .get("/api/cards")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body().jsonPath().get();
        return cardList.size();
    }

    public void MoneyTransfer(DbHelper.TransferInfo info) {
        given()
                .spec(requestSpec)
                .header(
                        "Authorization",
                        "Bearer " + token)
                .header("Content-Type",
                        "application/json")
                .body(info)
                .when()
                .post("/api/transfer")
                .then()
                .statusCode(200);
    }
}
