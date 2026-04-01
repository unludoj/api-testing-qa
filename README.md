# API Testing QA

![CI](https://github.com/unludoj/api-testing-qa/actions/workflows/ci.yml/badge.svg)

Testes automatizados de API REST escritos em Java com RestAssured, JUnit 5 e relatórios visuais via Allure.

---

## Tecnologias

| Tecnologia | Versão | Função |
|---|---|---|
| Java | 17 | Linguagem principal |
| RestAssured | 5.4.0 | Chamadas HTTP nos testes |
| JUnit 5 | 5.10.2 | Framework de testes |
| Allure Report | 2.27.0 | Relatório visual dos testes |
| Maven | 3.x | Build e gerenciamento de dependências |

---

## API testada

[JSONPlaceholder](https://jsonplaceholder.typicode.com) — API pública e gratuita. Não requer autenticação nem configuração extra.

---

## Cenários cobertos

| Método | Endpoint | Cenário |
|---|---|---|
| GET | `/users` | Lista todos os usuários e valida campos obrigatórios |
| GET | `/users` | Valida Content-Type da resposta |
| GET | `/users/1` | Busca usuário por ID existente |
| GET | `/users/999` | Retorna 404 para ID inexistente |
| POST | `/users` | Cria novo usuário e retorna 201 com os dados enviados |
| POST | `/users` | Valida que a resposta contém um ID gerado |
| PUT | `/users/1` | Atualiza dados do usuário e retorna 200 |
| DELETE | `/users/1` | Deleta usuário existente e retorna 200 |

---

## Como usar o RestAssured

Os testes seguem o padrão `given / when / then`, que separa claramente a configuração da requisição, a chamada e a validação da resposta:

```java
given()
    .contentType("application/json")
    .body(novoUsuario)
.when()
    .post("/users")
.then()
    .statusCode(201)
    .body("name", equalTo("Maria Souza"));
```

A classe `BaseTest` centraliza a configuração da URL base e dos logs, evitando repetição em cada arquivo de teste.

---

## Como rodar

**Pré-requisitos:** Java 17+ e Maven 3.x

```bash
git clone https://github.com/unludoj/api-testing-qa.git
cd api-testing-qa
mvn test
```

### Relatório Allure

Após rodar os testes, abra o relatório no navegador:

```bash
allure serve target/allure-results
```

Ou gere como arquivo estático:

```bash
mvn allure:report
# Relatório gerado em: target/site/allure-maven-plugin/index.html
```

> O [Allure CLI](https://allurereport.org/docs/install/) precisa estar instalado para o comando `allure serve`.

---

## Estrutura do projeto

```
api-testing-qa/
├── src/
│   └── test/
│       └── java/
│           └── com/qaportfolio/
│               ├── BaseTest.java      # Configuração base do RestAssured
│               └── UsersTest.java     # Testes do endpoint /users
├── .github/
│   └── workflows/
│       └── ci.yml                     # Pipeline CI/CD com GitHub Actions
└── pom.xml
```

---

## CI/CD

O projeto conta com pipeline no GitHub Actions que executa os testes automaticamente a cada `push` ou `pull request` na branch `main`.

O relatório Allure é publicado automaticamente no **GitHub Pages** após cada execução, mesmo que algum teste falhe.

---

## Próximos passos

- [ ] Testes para `/posts` e `/comments`
- [ ] Cenários negativos com payloads inválidos
- [ ] Parametrização com `@ParameterizedTest`
- [ ] Cenários avançados com WireMock
