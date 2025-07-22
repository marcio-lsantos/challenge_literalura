# 📚 Biblioteca Gutendex - Spring Boot

Aplicação Java com Spring Boot que consome a [API Gutendex](https://gutendex.com/) para buscar livros, registrar autores e obras em um banco de dados local, além de oferecer estatísticas e filtros de consulta.

---

## 🚀 Funcionalidades

- 🔍 Buscar livros por título na API Gutendex
- 💃️ Registrar livros e autores em banco de dados
- 📚 Listar todos os livros registrados
- 👨‍💼 Listar todos os autores registrados
- 📅 Listar autores vivos em um determinado ano
- 🌐 Filtrar livros por idioma
- 📈 Exibir os 10 livros mais baixados
- 📊 Gerar estatísticas de downloads (máximo, mínimo e média)

---

## 🛠️ Tecnologias utilizadas

- Java 17+
- Spring Boot
- JPA / Spring Data
- PostgreSQL (configurável via `.env`)
- [Gutendex API](https://gutendex.com/)
- Jackson (para conversão JSON)



---

## 📂 Estrutura do Projeto

```
src/
├— main/
│   ├— java/
│   │   ├— Principal.java           # Classe principal com o menu
│   │   ├— ConsumoApi.java         # Realiza chamadas à API Gutendex
│   │   ├— ConversorDados.java     # Converte JSON para objetos
│   │   ├— model/                  # Entidades JPA (Autores, Livros)
│   │   ├— repository/             # Repositórios Spring Data JPA
│   ├— resources/
│       ├— application.properties
     
```

---

## ⚙️ Configuração do Banco de Dados

Você pode usar variáveis de ambiente com um arquivo `.env`:

### `.env`

```env
# .env
DB_URL=jdbc:postgresql://localhost:5432/banco_de_dados
DB_USERNAME=usuário
DB_PASSWORD=senha
```

### `application.properties`

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
hibernate.dialect=org.hibernate.dialect.HSQLDialect

spring.jpa.hibernate.ddl-auto=update
```

---

## ▶️ Como executar

### 1. Clonar o projeto

```bash
git clone https://github.com/marcio-lsantos/challenge_literalura.git
```

### 2. Configurar o ambiente

- Crie o arquivo `.env` com as variáveis do banco de dados
- Ou defina variáveis de ambiente no terminal

### 3. Executar com Maven ou IDE

```bash
./mvnw spring-boot:run
```

Ou rode diretamente pela sua IDE (IntelliJ, Eclipse, VS Code etc.).

---

## 🧪 Testar funcionalidade principal

Ao rodar, será exibido um menu interativo no console:

```text
1 - Buscar livro por título
2 - Listar livros registrados
3 - Listar autores registrados
...
```

Digite `1` e procure, por exemplo: `Don Quixote`.

---

## 📝 Notas adicionais

- O projeto utiliza registros (`record`) Java para representar objetos de dados vindos da API.
- A persistência dos dados está desacoplada da API: livros e autores podem ser buscados, salvos e listados localmente.

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

---

##
