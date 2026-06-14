# Player API

API REST em Java + Spring Boot para buscar dados de jogadores do Minecraft diretamente da API oficial da Mojang, incluindo UUID, skin, signature e cape.

---

## Tecnologias

- Java 17
- Spring Boot 3.3.4
- Lombok
- API oficial da Mojang (SessionServer)

---

## Endpoints

### GET /player/{nick}

Retorna os dados do jogador com o nick informado.

Exemplo de requisição:
```
GET /player/Notch
```

Resposta de sucesso (200 OK):
```json
{
  "success": true,
  "response": {
    "nick": "Notch",
    "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
    "premium": true,
    "skinUrl": "http://textures.minecraft.net/texture/...",
    "skinModel": "classic",
    "signature": "BASE64...",
    "capeUrl": null
  }
}
```

Resposta de erro (404 Not Found):
```json
{
  "success": false,
  "error_code": 404,
  "response": {
    "message": "Not Found",
    "status": 404
  }
}
```

---

## Como rodar

1. Clone o repositório:
```bash
git clone https://github.com/ggaaraujodev/player-api.git
cd player-api
```

2. Rode com o Maven Wrapper:

Windows:
```cmd
mvnw spring-boot:run
```

Linux/Mac:
```bash
./mvnw spring-boot:run
```

3. Acesse:
```
http://localhost:8080/player/{nick}
```

---

## Cache

Para evitar abusar do rate limit da Mojang (~200 req/min), a API utiliza cache em memória com expiração de 5 minutos por nick consultado.

---

## Observações

- Um jogador é considerado premium se a Mojang retornar um UUID válido para o nick informado.
- A signature é assinada com a chave privada da Mojang (RSA) e pode ser usada para verificar autenticidade da skin no servidor.
- O campo skinModel retorna "slim" (Alex) ou "classic" (Steve).
- capeUrl retorna null caso o jogador não possua capa.

---

## Licença

MIT
