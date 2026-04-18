package org.example.tests;

import io.restassured.response.Response;
import org.example.base.BaseTest;
import org.example.client.DogApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para o endpoint GET /breed/{breed}/images
 */
@DisplayName("GET /breed/{breed}/images")
class BreedImagesTest extends BaseTest {

    private DogApiClient client;

    @BeforeEach
    void init() {
        client = new DogApiClient();
    }

    @Nested
    @DisplayName("Cenários com raça válida")
    class ValidBreed {

        private static final String VALID_BREED = "hound";

        @Test
        @DisplayName("Deve retornar status code 200 para raça válida")
        void shouldReturnStatusCode200() {
            Response response = client.getBreedImages(VALID_BREED);

            assertEquals(200, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve retornar status 'success'")
        void shouldReturnStatusSuccess() {
            Response response = client.getBreedImages(VALID_BREED);

            assertEquals("success", response.jsonPath().getString("status"));
        }

        @Test
        @DisplayName("Deve retornar lista de imagens não vazia")
        void shouldReturnNonEmptyImagesList() {
            Response response = client.getBreedImages(VALID_BREED);

            List<String> images = response.jsonPath().getList("message");
            assertNotNull(images);
            assertFalse(images.isEmpty(), "Lista de imagens não deve estar vazia");
        }

        @Test
        @DisplayName("Todas as URLs devem começar com https://")
        void allUrlsShouldStartWithHttps() {
            Response response = client.getBreedImages(VALID_BREED);

            List<String> images = response.jsonPath().getList("message");
            for (String url : images) {
                assertTrue(url.startsWith("https://"),
                        "URL deve começar com https://: " + url);
            }
        }

        @Test
        @DisplayName("Todas as URLs devem ser de imagens válidas")
        void allUrlsShouldBeValidImages() {
            Response response = client.getBreedImages(VALID_BREED);

            List<String> images = response.jsonPath().getList("message");
            for (String url : images) {
                assertTrue(url.matches(".*\\.(jpg|jpeg|png|gif)$"),
                        "URL deve ser de imagem: " + url);
            }
        }

        @Test
        @DisplayName("Deve estar em conformidade com o JSON Schema")
        void shouldMatchJsonSchema() {
            Response response = client.getBreedImages(VALID_BREED);

            response.then().body(matchesJsonSchemaInClasspath("schemas/breed-images.json"));
        }
    }

    @Nested
    @DisplayName("Cenários com raça inválida")
    class InvalidBreed {

        private static final String INVALID_BREED = "invalidbreed123";

        @Test
        @DisplayName("Deve retornar status code 404 para raça inválida")
        void shouldReturnStatusCode404() {
            Response response = client.getBreedImages(INVALID_BREED);

            assertEquals(404, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve retornar status 'error'")
        void shouldReturnStatusError() {
            Response response = client.getBreedImages(INVALID_BREED);

            assertEquals("error", response.jsonPath().getString("status"));
        }

        @Test
        @DisplayName("Deve retornar mensagem de erro")
        void shouldReturnErrorMessage() {
            Response response = client.getBreedImages(INVALID_BREED);

            String message = response.jsonPath().getString("message");
            assertNotNull(message);
            assertFalse(message.isEmpty(), "Mensagem de erro não deve estar vazia");
        }

        @Test
        @DisplayName("Deve estar em conformidade com o JSON Schema de erro")
        void shouldMatchErrorJsonSchema() {
            Response response = client.getBreedImages(INVALID_BREED);

            response.then().body(matchesJsonSchemaInClasspath("schemas/error.json"));
        }
    }
}
