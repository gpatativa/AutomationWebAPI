# Dog API Tests 🐕

Projeto de automação de testes para validação da [Dog API](https://dog.ceo/dog-api/documentation).

## Stack

- Java 17
- Maven
- JUnit 5
- RestAssured
- Allure Report

## Pré-requisitos

- JDK 17+
- Maven 3.8+

## Estrutura do Projeto

```
src/test/java/org/example/
├── base/
│   └── BaseTest.java          # Configuração base do RestAssured
├── client/
│   └── DogApiClient.java      # Cliente HTTP para a API
├── config/
│   └── Config.java            # Configurações (URL base)
└── tests/
    ├── BreedsListTest.java    # Testes GET /breeds/list/all
    ├── BreedImagesTest.java   # Testes GET /breed/{breed}/images
    └── RandomImageTest.java   # Testes GET /breeds/image/random
```

## Executar Testes

```bash
# Windows
.\mvnw.cmd test

# Linux/macOS
./mvnw test
```

## Relatório Allure

```bash
# Windows — gerar e abrir no navegador
.\mvnw.cmd allure:serve

# Windows — apenas gerar
.\mvnw.cmd allure:report
# Relatório em: target/site/allure-maven-plugin/index.html

# Linux/macOS
./mvnw allure:serve
./mvnw allure:report
```

> **Nota:** Use sempre `.\mvnw.cmd` (Windows) ou `./mvnw` (Linux/macOS) no lugar de `mvn`, pois o Maven pode não estar instalado no PATH do sistema.

## Configurar URL Base

Por padrão: `https://dog.ceo/api`

Para alterar:

```bash
# Linux/macOS
export DOG_API_BASE_URL=https://dog.ceo/api

# Windows PowerShell
$env:DOG_API_BASE_URL = "https://dog.ceo/api"

# Windows CMD
set DOG_API_BASE_URL=https://dog.ceo/api
```

## Cenários de Teste

### GET /breeds/list/all
- ✅ Status code 200
- ✅ Status = "success"
- ✅ Campo message existe
- ✅ Lista de raças não vazia
- ✅ Contém raças conhecidas

### GET /breed/{breed}/images
- ✅ Status code 200 (raça válida)
- ✅ Status = "success"
- ✅ Lista de imagens não vazia
- ✅ URLs começam com https://
- ✅ Status code 404 (raça inválida)
- ✅ Status = "error" (raça inválida)

### GET /breeds/image/random
- ✅ Status code 200
- ✅ Status = "success"
- ✅ URL válida de imagem

## CI/CD

O projeto usa GitHub Actions para execução automática dos testes em push e pull requests.
