package org.example.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.example.config.Config;
import org.junit.jupiter.api.BeforeAll;

/**
 * Classe base para configuração do RestAssured.
 */
public abstract class BaseTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = Config.getBaseUrl();
        RestAssured.defaultParser = io.restassured.parsing.Parser.JSON;
        RestAssured.filters(
                new AllureRestAssured(),
                new RequestLoggingFilter(LogDetail.METHOD),
                new RequestLoggingFilter(LogDetail.URI),
                new ResponseLoggingFilter(LogDetail.STATUS),
                new ResponseLoggingFilter(LogDetail.BODY)
        );
    }
}

