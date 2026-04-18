package org.example.client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Cliente para chamadas à Dog API.
 */
public class DogApiClient {

    public Response getAllBreeds() {
        return given()
                .when()
                .get("/breeds/list/all");
    }

    public Response getBreedImages(String breed) {
        return given()
                .pathParam("breed", breed)
                .when()
                .get("/breed/{breed}/images");
    }

    public Response getRandomImage() {
        return given()
                .when()
                .get("/breeds/image/random");
    }
}

