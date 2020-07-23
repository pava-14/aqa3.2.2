package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.restassured.authentication.OAuthSignature;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import lombok.val;
import ru.netology.data.DbHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

import static com.codeborne.selenide.Selenide.$;
import static io.restassured.RestAssured.given;
import static java.awt.SystemColor.info;
import static javax.swing.UIManager.get;
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

    public void ShowCards() {
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
                        .statusCode(200) // OK
                        .extract()
                        .body().jsonPath().get();
        int expected = 2;
        assertEquals(expected, cardList.size());
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
                .statusCode(200); // OK
        int i =0;
    }
}
