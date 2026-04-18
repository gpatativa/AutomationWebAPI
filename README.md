# Dog API Tests 🐕

Projeto de automação de testes para validação da [Dog API](https://dog.ceo/dog-api/documentation).

## Stack

- Java 17
- Maven 3.9
- JUnit 5
- RestAssured 5.4
- Allure Report 2.25
- JSON Schema Validator

## Pré-requisitos

- JDK 17+
- Maven 3.8+ no PATH

## Estrutura do Projeto

```
src/test/
├── java/org/example/
│   ├── base/
│   │   └── BaseTest.java          # Configuração base do RestAssured e filtros de log
│   ├── client/
│   │   └── DogApiClient.java      # Cliente HTTP centralizado para a API
│   ├── config/
│   │   └── Config.java            # URL base via variável de ambiente
│   └── tests/
│       ├── BreedsListTest.java    # Testes GET /breeds/list/all
│       ├── BreedImagesTest.java   # Testes GET /breed/{breed}/images
│       └── RandomImageTest.java   # Testes GET /breeds/image/random
└── resources/
    ├── schemas/
    │   ├── breeds-list.json       # JSON Schema — listagem de raças
    │   ├── breed-images.json      # JSON Schema — imagens por raça
    │   ├── random-image.json      # JSON Schema — imagem aleatória
    │   └── error.json             # JSON Schema — resposta de erro
    └── allure.properties          # Configuração do Allure
```

## Executar Testes

```bash
mvn test
```

## Relatório Allure

**Gerar e abrir no navegador:**

```bash
mvn allure:serve
```

**Apenas gerar** (sem abrir):

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
- ✅ Status = `"success"`
- ✅ Campo `message` existe
- ✅ Lista de raças não vazia
- ✅ Contém raças conhecidas (hound, bulldog)
- ✅ Conformidade com JSON Schema

### GET /breed/{breed}/images — raça válida (`hound`)
- ✅ Status code 200
- ✅ Status = `"success"`
- ✅ Lista de imagens não vazia
- ✅ Todas as URLs começam com `https://`
- ✅ Todas as URLs são de imagens válidas (jpg, jpeg, png, gif)
- ✅ Conformidade com JSON Schema

### GET /breed/{breed}/images — raça inválida
- ✅ Status code 404
- ✅ Status = `"error"`
- ✅ Mensagem de erro presente
- ✅ Conformidade com JSON Schema de erro

### GET /breeds/image/random
- ✅ Status code 200
- ✅ Status = `"success"`
- ✅ URL começa com `https://`
- ✅ URL aponta para imagem válida
- ✅ URL pertence ao domínio `images.dog.ceo`
- ✅ Conformidade com JSON Schema

## CI/CD

O projeto usa GitHub Actions para execução automática dos testes a cada push ou pull request na branch `master`. O relatório Allure é gerado e salvo como artifact.
