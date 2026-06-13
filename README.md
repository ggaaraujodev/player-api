# Player API

API REST desenvolvida em **Java com Spring Boot** para consulta de estatísticas de jogadores a partir de um banco de dados de um servidor de Minecraft.

O projeto foi criado como exercício de portfólio, com foco em boas práticas de arquitetura (separação em camadas Controller/Request/Response), persistência com MySQL e estrutura básica de uma API REST com Spring Boot.

## Funcionalidades

- Consulta de estatísticas de jogadores via endpoint REST
- Estrutura simples para estudo de Spring Boot
- Fácil de adaptar para outros bancos ou plugins de Minecraft

## Tecnologias

- Java 17
- Spring Boot
- MySQL
- Maven

## Como executar

### Pré-requisitos
- [JDK 17](https://www.oracle.com/java/technologies/downloads/)
- MySQL (pode usar o XAMPP)
- IntelliJ IDEA ou outra IDE Java

### Passo a passo

```bash
# Clone o repositório
git clone https://github.com/ggaaraujodev/PlayerAPI.git
cd PlayerAPI
```

Configure a conexão com o banco de dados no arquivo:
```
src/main/resources/application.properties
```

Altere conforme seu banco:
- Nome da database
- Usuário
- Senha
- Porta (caso seja diferente da padrão)

Abra o projeto no IntelliJ IDEA, localize a classe `Application` e clique em **Run**.

A API estará disponível em `http://localhost:8080`.

## Endpoints

### Consultar estatísticas de um jogador
```
GET /player/{nick}
```

**Exemplo:**
```
GET /player/Steve
```

## Estrutura do projeto

- **Users** — entidade que representa o usuário no banco
- **UserRequest** — classe usada para requisições
- **UserResponse** — classe usada para respostas da API
- **DataController** — controller responsável pelos endpoints da API

## Possíveis melhorias futuras

- Documentação interativa via Swagger
- Testes unitários (JUnit + Mockito)
- Tratamento de erros mais robusto
