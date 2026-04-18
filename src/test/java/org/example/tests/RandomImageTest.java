package org.example.tests;

import io.restassured.response.Response;
import org.example.base.BaseTest;
import org.example.client.DogApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para o endpoint GET /breeds/image/random
 */
@DisplayName("GET /breeds/image/random")
class RandomImageTest extends BaseTest {

    private DogApiClient client;

    @BeforeEach
    void init() {
        client = new DogApiClient();
    }

    @Test
    @DisplayName("Deve retornar status code 200")
    void shouldReturnStatusCode200() {
        Response response = client.getRandomImage();

        assertEquals(200, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar status 'success'")
    void shouldReturnStatusSuccess() {
        Response response = client.getRandomImage();

        assertEquals("success", response.jsonPath().getString("status"));
    }

    @Test
    @DisplayName("Deve retornar URL válida no campo message")
    void shouldReturnValidUrl() {
        Response response = client.getRandomImage();

        String imageUrl = response.jsonPath().getString("message");
        assertNotNull(imageUrl);
        assertTrue(imageUrl.startsWith("https://"),
                "URL deve começar com https://: " + imageUrl);
    }

    @Test
    @DisplayName("Deve retornar URL de imagem válida")
    void shouldReturnValidImageUrl() {
        Response response = client.getRandomImage();

        String imageUrl = response.jsonPath().getString("message");
        assertTrue(imageUrl.matches(".*\\.(jpg|jpeg|png|gif)$"),
                "URL deve ser de imagem: " + imageUrl);
    }

    @Test
    @DisplayName("URL deve apontar para domínio da Dog API")
    void urlShouldPointToDogApiDomain() {
        Response response = client.getRandomImage();

        String imageUrl = response.jsonPath().getString("message");
        assertTrue(imageUrl.contains("images.dog.ceo"),
                "URL deve ser do domínio images.dog.ceo: " + imageUrl);
    }

    @Test
    @DisplayName("Deve estar em conformidade com o JSON Schema")
    void shouldMatchJsonSchema() {
        Response response = client.getRandomImage();

        response.then().body(matchesJsonSchemaInClasspath("schemas/random-image.json"));
    }
}
