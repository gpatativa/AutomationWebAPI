package org.example.config;

/**
 * Configuração central do projeto.
 */
public final class Config {

    private static final String DEFAULT_BASE_URL = "https://dog.ceo/api";

    private Config() {
    }

    public static String getBaseUrl() {
        String url = System.getenv("DOG_API_BASE_URL");
        return (url != null && !url.isBlank()) ? url : DEFAULT_BASE_URL;
    }
}

