# API Testing QA — Portfolio

Projeto de testes automatizados de API REST utilizando **RestAssured**, **JUnit 5** e **Allure Report**.

Desenvolvido como projeto de portfólio para demonstrar habilidades em QA com foco em testes de API.

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Função |
|---|---|---|
| Java | 17 | Linguagem principal |
| RestAssured | 5.4.0 | Realiza as chamadas HTTP nos testes |
| JUnit 5 | 5.10.2 | Framework de testes |
| Allure Report | 2.27.0 | Geração de relatório visual dos testes |
| Maven | 3.x | Build e gerenciamento de dependências |

---

## 🧪 Cenários de Teste

API testada: [JSONPlaceholder](https://jsonplaceholder.typicode.com) — endpoint `/users`

| Método | Endpoint | Cenário |
|---|---|---|
| GET | `/users` | Retorna lista com 10 usuários e campos obrigatórios |
| GET | `/users` | Content-Type da resposta é `application/json` |
| GET | `/users/1` | Retorna dados corretos do usuário por ID |
| GET | `/users/999` | Retorna 404 para ID inexistente |
| POST | `/users` | Cria novo usuário e retorna 201 com dados |
| POST | `/users` | Resposta contém ID do novo recurso criado |
| PUT | `/users/1` | Atualiza usuário e retorna 200 com dados atualizados |
| DELETE | `/users/1` | Remove usuário e retorna 200 |

---

## ▶️ Como executar

### Pré-requisitos
- Java 17+
- Maven 3.x

### Rodando os testes

```bash
mvn test
```

### Gerando o relatório Allure

1. Instale o [Allure CLI](https://allurereport.org/docs/install/)

2. Rode os testes para gerar os resultados:
```bash
mvn test
```

3. Abra o relatório no navegador:
```bash
allure serve target/allure-results
```

Ou gere como arquivo estático:
```bash
mvn allure:report
# Relatório gerado em: target/site/allure-maven-plugin/index.html
```

---

## 📂 Estrutura do Projeto

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

## 🔁 CI/CD

O projeto conta com pipeline no GitHub Actions que executa os testes automaticamente a cada `push` ou `pull request` na branch `main`.

O relatório Allure é publicado automaticamente no **GitHub Pages** após cada execução.

[![CI](../../actions/workflows/ci.yml/badge.svg)](../../actions/workflows/ci.yml)
