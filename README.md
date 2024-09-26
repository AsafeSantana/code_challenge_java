# Documentação do Projeto

## Tecnologias Utilizadas

### Backend
- **Java**: 17
- **Spring Boot**: Framework para construção de aplicações Java.
- **Banco de Dados**: H2 (para desenvolvimento e testes).
- **Docker**: Contêineres para facilitar a implantação.

### Requisitos
- **Java**: 17
- **Docker**: Instalação do Docker necessária.
- **Node.js**: 16.15.0
- **NPM**: 8.5.5

## Instruções de Inicialização

### Clonando o Repositório
Clone o repositório do projeto para a sua máquina local:
```bash
git clone <https://github.com/AsafeSantana/code_challenge_java.git>
```
### Backend
Para rodar a API do backend, execute o seguinte comando no terminal:
```bash
docker run -p 8080:8080 springio/code-challenge-backend
```

**Utilize o Postman para testar os endpoints da API. Abaixo estão alguns exemplos de requisições:**

**Exemplo de requisição para criar uma task:**
```json
{
  "title": "Tarefa 1",
  "description": "Descrição tarefa",
  "color": "#FFFFFF",
  "favorite": true
}
```
| Método | Descrição                         | Endpoint                               |
|--------|-----------------------------------|----------------------------------------|
| POST   | Criar task                       | `localhost:8080/api/tasks`            |
| GET    | Listar todas as tasks           | `localhost:8080/api/tasks`            |
| PUT    | Atualizar task                   | `localhost:8080/api/tasks/{id}`       |
| DELETE | Deletar task                     | `localhost:8080/api/tasks/{id}`       |
| GET    | Listar tasks favoritas           | `localhost:8080/api/tasks/favorites`  |
| GET    | Listar tasks pela cor           | `localhost:8080/api/tasks/color/{color}`  |
| PATCH  | Alterar cor da task              | `localhost:8080/api/tasks/{id}/color` |

**Testes Unitários:**
Testes unitários foram implementados utilizando JUnit e Mockito.

### Frontend
Para iniciar a aplicação frontend, execute o seguinte comando no terminal:
```bash
docker run -p 3000:80 code-challenge-frontend
```

**Em seguida, abra o seu navegador e acesse http://localhost:3000 para testar a aplicação.**

