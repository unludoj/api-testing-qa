package com.qaportfolio;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Testes para o endpoint /users da JSONPlaceholder API.
 *
 * Endpoint base: https://jsonplaceholder.typicode.com/users
 *
 * Cenários cobertos:
 *  - Listar todos os usuários
 *  - Buscar usuário por ID (encontrado e não encontrado)
 *  - Criar usuário (POST)
 *  - Atualizar usuário (PUT)
 *  - Deletar usuário (DELETE)
 */
@Epic("JSONPlaceholder API")
@Feature("Users")
@DisplayName("Testes do endpoint /users")
class UsersTest extends BaseTest {

    // -------------------------------------------------------------------------
    // GET /users
    // -------------------------------------------------------------------------

    @Test
    @Story("Listar usuários")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifica se GET /users retorna uma lista com exatamente 10 usuários e os campos obrigatórios")
    @DisplayName("GET /users — deve retornar lista com 10 usuários")
    void deveRetornarListaDeUsuarios() {
        buscarTodosOsUsuarios();
    }

    @Step("GET /users — verificar lista com 10 usuários e campos obrigatórios")
    void buscarTodosOsUsuarios() {
        given()
            .when()
                .get("/users")
            .then()
                .statusCode(200)
                // A API sempre retorna exatamente 10 usuários
                .body("", hasSize(10))
                // Garante que cada item tem os campos esperados
                .body("[0].id",    notNullValue())
                .body("[0].name",  notNullValue())
                .body("[0].email", notNullValue());
    }

    @Test
    @Story("Listar usuários")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifica se a resposta do GET /users possui Content-Type application/json")
    @DisplayName("GET /users — resposta deve ter Content-Type JSON")
    void respostaDeveSerJson() {
        verificarContentTypeJson();
    }

    @Step("GET /users — verificar Content-Type JSON")
    void verificarContentTypeJson() {
        given()
            .when()
                .get("/users")
            .then()
                .statusCode(200)
                .contentType("application/json");
    }

    // -------------------------------------------------------------------------
    // GET /users/{id}
    // -------------------------------------------------------------------------

    @Test
    @Story("Buscar usuário por ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifica se GET /users/1 retorna os dados corretos do usuário de ID 1")
    @DisplayName("GET /users/1 — deve retornar o usuário correto")
    void deveRetornarUsuarioPorId() {
        buscarUsuarioPorId(1);
    }

    @Step("GET /users/{id} — verificar dados do usuário")
    void buscarUsuarioPorId(int id) {
        given()
            .when()
                .get("/users/" + id)
            .then()
                .statusCode(200)
                .body("id",       equalTo(1))
                .body("name",     equalTo("Leanne Graham"))
                .body("username", equalTo("Bret"))
                .body("email",    equalTo("Sincere@april.biz"));
    }

    @Test
    @Story("Buscar usuário por ID")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifica se GET /users/999 retorna 404 para um ID que não existe")
    @DisplayName("GET /users/999 — deve retornar 404 para ID inexistente")
    void deveRetornar404ParaUsuarioInexistente() {
        buscarUsuarioInexistente(999);
    }

    @Step("GET /users/{id} — verificar 404 para ID inexistente")
    void buscarUsuarioInexistente(int id) {
        given()
            .when()
                .get("/users/" + id)
            .then()
                // JSONPlaceholder retorna 404 para recursos inexistentes
                .statusCode(404);
    }

    // -------------------------------------------------------------------------
    // POST /users
    // -------------------------------------------------------------------------

    @Test
    @Story("Criar usuário")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifica se POST /users cria um novo usuário e retorna status 201 com os dados enviados")
    @DisplayName("POST /users — deve criar usuário e retornar 201")
    void deveCriarNovoUsuario() {
        String novoUsuario = """
                {
                  "name": "Maria Souza",
                  "username": "mariasouza",
                  "email": "maria@email.com"
                }
                """;

        criarUsuario(novoUsuario, "Maria Souza", "mariasouza", "maria@email.com");
    }

    @Step("POST /users — criar usuário {nome}")
    void criarUsuario(String body, String nome, String username, String email) {
        given()
            .contentType("application/json")
            .body(body)
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            // A API devolve o objeto criado com um ID gerado
            .body("id",       notNullValue())
            .body("name",     equalTo(nome))
            .body("username", equalTo(username))
            .body("email",    equalTo(email));
    }

    @Test
    @Story("Criar usuário")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifica se o POST /users retorna um ID no corpo da resposta")
    @DisplayName("POST /users — deve retornar o ID do novo usuário no corpo")
    void postDeveRetornarIdNaResposta() {
        String novoUsuario = """
                {
                  "name": "Carlos Lima",
                  "email": "carlos@email.com"
                }
                """;

        verificarIdNaResposta(novoUsuario);
    }

    @Step("POST /users — verificar presença de ID na resposta")
    void verificarIdNaResposta(String body) {
        given()
            .contentType("application/json")
            .body(body)
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            // JSONPlaceholder sempre retorna id=11 para simulação de criação
            .body("id", equalTo(11));
    }

    // -------------------------------------------------------------------------
    // PUT /users/{id}
    // -------------------------------------------------------------------------

    @Test
    @Story("Atualizar usuário")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifica se PUT /users/1 atualiza os dados do usuário e retorna status 200")
    @DisplayName("PUT /users/1 — deve atualizar usuário e retornar 200")
    void deveAtualizarUsuario() {
        String usuarioAtualizado = """
                {
                  "id": 1,
                  "name": "Leanne Graham Atualizado",
                  "username": "Bret",
                  "email": "novo@email.com"
                }
                """;

        atualizarUsuario(1, usuarioAtualizado);
    }

    @Step("PUT /users/{id} — atualizar dados do usuário")
    void atualizarUsuario(int id, String body) {
        given()
            .contentType("application/json")
            .body(body)
        .when()
            .put("/users/" + id)
        .then()
            .statusCode(200)
            .body("name",  equalTo("Leanne Graham Atualizado"))
            .body("email", equalTo("novo@email.com"));
    }

    // -------------------------------------------------------------------------
    // DELETE /users/{id}
    // -------------------------------------------------------------------------

    @Test
    @Story("Deletar usuário")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifica se DELETE /users/1 remove o usuário e retorna status 200")
    @DisplayName("DELETE /users/1 — deve deletar usuário e retornar 200")
    void deveDeletarUsuario() {
        deletarUsuario(1);
    }

    @Step("DELETE /users/{id} — verificar remoção do usuário")
    void deletarUsuario(int id) {
        given()
            .when()
                .delete("/users/" + id)
            .then()
                // JSONPlaceholder retorna 200 com body vazio para DELETE
                .statusCode(200);
    }
}
