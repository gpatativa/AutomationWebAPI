package org.example.tests;

import io.restassured.response.Response;
import org.example.base.BaseTest;
import org.example.client.DogApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para o endpoint GET /breeds/list/all
 */
@DisplayName("GET /breeds/list/all")
class BreedsListTest extends BaseTest {

    private DogApiClient client;

    @BeforeEach
    void init() {
        client = new DogApiClient();
    }

    @Test
    @DisplayName("Deve retornar status code 200")
    void shouldReturnStatusCode200() {
        Response response = client.getAllBreeds();

        assertEquals(200, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar status 'success'")
    void shouldReturnStatusSuccess() {
        Response response = client.getAllBreeds();

        assertEquals("success", response.jsonPath().getString("status"));
    }

    @Test
    @DisplayName("Deve retornar campo message existente")
    void shouldReturnMessageField() {
        Response response = client.getAllBreeds();

        assertNotNull(response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Deve retornar lista de raças não vazia")
    void shouldReturnNonEmptyBreedsList() {
        Response response = client.getAllBreeds();

        Map<String, Object> breeds = response.jsonPath().getMap("message");
        assertNotNull(breeds);
        assertFalse(breeds.isEmpty(), "Lista de raças não deve estar vazia");
    }

    @Test
    @DisplayName("Deve conter raças conhecidas como hound e bulldog")
    void shouldContainKnownBreeds() {
        Response response = client.getAllBreeds();

        Map<String, Object> breeds = response.jsonPath().getMap("message");
        assertTrue(breeds.containsKey("hound"), "Deve conter a raça hound");
        assertTrue(breeds.containsKey("bulldog"), "Deve conter a raça bulldog");
    }

    @Test
    @DisplayName("Deve estar em conformidade com o JSON Schema")
    void shouldMatchJsonSchema() {
        Response response = client.getAllBreeds();

        response.then().body(matchesJsonSchemaInClasspath("schemas/breeds-list.json"));
    }
}
