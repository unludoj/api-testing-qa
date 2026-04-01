package com.qaportfolio;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

/**
 * Classe base herdada por todos os testes.
 * Centraliza a configuração do RestAssured para não repetir em cada arquivo.
 */
public class BaseTest {

    @BeforeAll
    static void configurar() {
        // URL base da API pública JSONPlaceholder
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Habilita logs apenas quando o teste falha — mantém o console limpo
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
