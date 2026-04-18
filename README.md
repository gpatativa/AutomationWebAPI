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
- Maven 3.8+ no PATH

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
mvn test
```

## Relatório Allure

### Gerar e visualizar

**Opção recomendada:** gera e abre automaticamente no navegador via servidor local:

```bash
mvn allure:serve
```

**Apenas gerar** (sem abrir o navegador):

```bash
mvn allure:report
# Relatório salvo em: target/site/allure-maven-plugin/index.html
```

> ⚠️ **Não abra o `index.html` diretamente pelo Explorer de Arquivos.**
> O Allure carrega os dados via JavaScript e os navegadores bloqueiam esse tipo de chamada
> quando o protocolo é `file://`. Use sempre `mvn allure:serve` para visualizar localmente.

### Fluxo completo

```bash
# 1. Executar os testes (gera resultados em target/allure-results)
mvn test

# 2. Visualizar o relatório no navegador
mvn allure:serve
```

## Configurar URL Base

Por padrão: `https://dog.ceo/api`

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
