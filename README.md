# Player API

Este projeto é uma **API REST simples em Java utilizando Spring Boot** que retorna estatísticas de um jogador a partir de um banco de dados de um **servidor de Minecraft**.

O objetivo do projeto é demonstrar como criar uma API básica usando **Java, Spring Boot e MySQL**, permitindo consultar informações de jogadores de forma simples através de um endpoint.

---

## 🚀 Funcionalidades

* Buscar estatísticas de jogadores no banco de dados
* Endpoint REST para consulta de dados
* Estrutura simples para estudo de **Spring Boot**
* Fácil de adaptar para outros bancos ou plugins de Minecraft

---

## 📋 Pré-requisitos

Antes de executar o projeto, você precisa ter instalado:

* **JDK 17**
* **MySQL** (pode usar o XAMPP)
* **IntelliJ IDEA** ou outra IDE Java
* **Maven** (normalmente já incluso em projetos Spring Boot)

---

## ⚙️ Configuração

Antes de iniciar a aplicação, configure a conexão com o banco de dados.

Edite o arquivo:

```
src/main/resources/application.properties
```

E altere as seguintes informações de acordo com o seu banco de dados:

* Nome da database
* Usuário
* Senha
* Porta (caso seja diferente da padrão)

---

## ▶️ Executando o projeto

1. Abra o projeto no **IntelliJ IDEA**
2. Localize a classe `Application`
3. Clique em **Run**

O servidor será iniciado em:

```
http://localhost:8080
```

---

## 📡 Utilizando a API

Para consultar as estatísticas de um jogador, acesse o endpoint:

```
http://localhost:8080/player/{nick}
```

Exemplo:

```
http://localhost:8080/player/Steve
```

---

## 🛠 Estrutura do Projeto

Algumas partes importantes do projeto:

* **Users** → entidade que representa o usuário no banco
* **UserRequest** → classe usada para requisições
* **UserResponse** → classe usada para respostas da API
* **DataController** → controller responsável pelos endpoints da API

Você pode adaptar essas classes de acordo com os dados que deseja retornar do banco.
