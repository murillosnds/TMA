# 📝 TMA | Tasks Management API

<div style="display: flex; justify-content: center; align-items: center;">
<img src="https://i.imgur.com/7QMvdwE.png">
</div>
<br>

> 👨🏻‍💻 **Made by: Murillo Sergio**

![Java](https://img.shields.io/badge/Java-black?style=for-the-badge&logo=openjdk&logoColor=green)
![Spring Boot](https://img.shields.io/badge/springboot-000000?style=for-the-badge&logo=springboot&logoColor=green)
![JUnit](https://img.shields.io/badge/junit-000000?style=for-the-badge&logo=junit5&logoColor=green)
![Swagger](https://img.shields.io/badge/swagger-000000?style=for-the-badge&logo=swagger&logoColor=green)
![PostgreSQL](https://img.shields.io/badge/postgresql-000000?style=for-the-badge&logo=postgresql&logoColor=green)
![Docker](https://img.shields.io/badge/docker-000000?style=for-the-badge&logo=docker&logoColor=green)
![Argon2](https://img.shields.io/badge/argon2-000000?style=for-the-badge&logo=letsencrypt&logoColor=green)

## Endpoints: 

### User:
- `POST`: /users
- `GET`: /users/{id}
- `DELETE`: /users/{id}
- `PATCH`: /users/{id}
- `PATCH`: /users/{id}/password

### Task:
- `POST`: /tasks
- `GET`: /tasks
- `GET`: /tasks/{id}
- `DELETE`: /tasks/{id}
- `PATCH`: /tasks/{id}

### Login:

- `POST`: /auth/login

## Request
### `POST /users` Example:
```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "stringstring"
}
```

**Minimum password length: 12**


### `POST /tasks` Example:

```json
{
  "title": "Minha primeira task",
  "description": "Estudar Spring Security",
  "userId": 1
}
```

### `POST /auth/login` Example:

```json
{
  "email": "joao@email.com",
  "password": "stringstring"
}
```

**Response: token for use in BearerAuth authentication**

**Use in "Authorize" (Swagger) and Authorization (Postman)**

### `PATCH /users/{id}` Example:

```json
{
  "password": "JoaoJoaoJoao"
}
```

### `PATCH /users/{id}/password` Example:

```json
{
  "currentPassword": "JoaoJoaoJoao",
  "newPassword": "`5F5y#B6B[!,"
}
```

### `PATCH /tasks/{id}` Example:

```json
{
  "description": "Estudar Spring JPA"
}
```



## Response
- **200:** `OK`
- **201:** `Created with success`
- **400:** `The request syntax is incorrect`
- **401:** `Requires authentication (login required)`
- **403:** `"Forbidden – token expired or insufficient permissions"`

## Swagger
Access the interactive API documentation at:

```
http://localhost:8080/swagger-ui.html
```

## PostgreSQL (example)
![Postgresql-picture](https://i.imgur.com/fPl9Vy5.png)

## Prerequisites
- Java 21
- Maven
- PostgreSQL (running locally, with a `tma` database created)

## How to run the project

```bash
git clone https://github.com/murillosnds/TMA.git
cd TMA
```

### 1. Configure the environment variables

Copy the example file and edit it:

```bash
cp .env.example .env
```

Open `.env` and fill in your values:

```env
JWT_SECRET_KEY=<your-generated-key>
DB_URL=jdbc:postgresql://localhost:5432/tma
DB_USERNAME=postgres
DB_PASSWORD=your_password
```

### How to generate a secure key

Run the following command in your terminal:

#### Linux:

```bash
openssl rand -base64 32
```

#### Windows (Powershell):

```bash
$bytes = New-Object byte[] 32; [System.Security.Cryptography.RandomNumberGenerator]::Create().GetBytes($bytes); [Convert]::ToBase64String($bytes)
```

### 2. Run the application

#### Linux/macOS:
```bash
export $(cat .env | xargs) && ./mvnw spring-boot:run
```

#### Windows (PowerShell):
```powershell
Get-Content .env | ForEach-Object { $name, $value = $_ -split '=', 2; Set-Item -Path "env:$name" -Value $value }
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

## How to run the project with Docker Compose

### Follow the previous steps for generating the secure key and editing the .env file.

### Now, run:

```bash
docker compose up -d
```

### ⭐ If this project has been helpful to you, please consider giving the repository a star!

This project is licensed under the MIT License.  
See the file [LICENSE](./LICENSE) for more details.
