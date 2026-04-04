# 🔥 Stay Hard v1 CLI

> Sistema CLI de gerenciamento de hábitos - Fase Pré-Spring (Dias 1-70)

[![Java](https://img.shields.io/badge/Java-21-blue?style=for-the-badge&logo=openjdk)](https://adoptium.com)
[![Maven](https://img.shields.io/badge/Maven-3.9-orange?style=for-the-badge&logo=apachemaven)](https://maven.apache.org)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql)](https://postgresql.org)

---

## 📋 SOBRE

Este é o projeto **v1-cli** do Stay Hard System - uma aplicação CLI em Java para gerenciamento de hábitos, construída durante a fase Pré-Spring do roadmap.

### Funcionalidades

- ✅ CRUD completo de Hábitos
- ✅ Sistema de Usuários com login/logout
- ✅ Prioridades (LOW, MEDIUM, HIGH, CRITICAL)
- ✅ Status com transições válidas
- ✅ Sistema de streaks e XP
- ✅ Níveis de usuário
- ✅ Filtros por prioridade e status
- ✅ Estatísticas deCompletion environment
- ✅ Observer Pattern para eventos
- ✅ Strategy Pattern para filtros

---

## 🛠️ TECNOLOGIAS

| Tecnologia | Versão |
|------------|--------|
| Java | 21 |
| Maven | 3.9+ |
| PostgreSQL | 16 |
| JUnit | 5.10.2 |
| Mockito | 5.11.0 |
| H2 Database | 2.2.224 (testes) |

---

## 📁 ESTRUTURA DO PROJETO

```
stay-hard-v1-cli/
├── pom.xml                          # Maven config
├── scripts/
│   └── init-database.sql            # Script SQL de setup
├── src/main/java/com/stayhard/
│   ├── StayHardApp.java             # Main class
│   ├── domain/
│   │   ├── entities/                # Habit, User (Records)
│   │   ├── enums/                   # Priority, Status
│   │   ├── exceptions/              # Custom exceptions
│   │   ├── observer/                # Observer pattern
│   │   └── strategy/                # Strategy pattern
│   ├── repository/
│   │   ├── HabitRepository.java     # Interface
│   │   ├── UserRepository.java      # Interface
│   │   └── jdbc/                   # JDBC implementations
│   ├── service/                    # Business logic
│   └── ui/                         # CLI interfaces
└── src/test/java/                   # Unit + Integration tests
```

---

## 🚀 COMO RODAR

### Pré-requisitos

- Java 21+
- Maven 3.9+
- PostgreSQL (ou usar H2 em memória para testes)

### Configuração do Banco

1. Crie o banco no PostgreSQL:
```sql
CREATE DATABASE stayhard;
```

2. Ou use o script:
```bash
psql -U postgres -d stayhard -f scripts/init-database.sql
```

### Rodar a Aplicação

```bash
# Clone o repositório
cd stay-hard-v1-cli

# Compile
mvn compile

# Rode os testes
mvn test

# Execute
mvn exec:java
```

### Ou diretamente com Java

```bash
mvn clean package
java -jar target/stay-hard-v1-cli-1.0.0.jar
```

---

## 📊 RESULTADOS DOS TESTES

```
Tests run: 53, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

| Suite | Tests |
|-------|-------|
| HabitTest | 8 |
| UserTest | 10 |
| JdbcHabitRepositoryIntegrationTest | 8 |
| JdbcUserRepositoryIntegrationTest | 8 |
| HabitServiceTest | 11 |
| UserServiceTest | 8 |

---

## 🎓 CONCEITOS APRENDIDOS

### Java Features
- **Records**: Habit, User, HabitEvent
- **Streams API**: Filtragem e ordenação
- **Lambdas**: Callbacks e predicates
- **Pattern Matching**: Switch expressions

### Design Patterns
- **Strategy**: HabitFilter, filtros reutilizáveis
- **Observer**: Eventos de hábito
- **Repository**: Abstração de persistência
- **Factory**: Criação de entidades

### SOLID Principles
- **S**ingle Responsibility: Classes focadas
- **O**pen/Closed: Extensível sem modificar
- **L**iskov Substitution: Interfaces bem definidas
- **I**nterface Segregation: Contratos simples
- **D**ependency Inversion: Abstrações primeiro

### SQL & JDBC
- Conexão com PostgreSQL
- CRUD com PreparedStatements
- Transações
- Mapeamento objeto-relacional manual

---

## 📖 EXEMPLO DE USO

```
╔═══════════════════════════════════════════╗
║                                           ║
║   ███████╗███████╗ ██████╗ █████╗ ██████╗ ║
║   ██╔════╝██╔════╝██╔════╝██╔══██╗██╔══██╗║
║   █████╗  ███████╗██║     ███████║██████╔╝║
║   ██╔══╝  ╚════██║██║     ██╔══██║██╔═══╝ ║
║   ███████╗███████║╚██████╗██║  ██║██║     ║
║   ╚══════╝╚══════╝ ╚═════╝╚═╝  ╚═╝╚═╝     ║
║                                           ║
║         Stay Hard. Never Settle.          ║
║                                           ║
╚═══════════════════════════════════════════╝

✅ Connected to PostgreSQL database!
✅ Database tables initialized!

╔══════════════════════════════════════════╗
║               MAIN MENU                 ║
╚══════════════════════════════════════════╝

  1. Habit Management
  2. User Management
  3. Exit

Choose option:
```

---

## 🔄 PRÓXIMOS PASSOS

| Versão | Tecnologia | Status |
|--------|-----------|--------|
| v1-cli | Java CLI + JDBC | ✅ Completo |
| v2-servlet | Servlet + JSP | 📅 Próximo |
| v3-spring | Spring Boot | 📅 Futuro |

---

## 📞 CONTATO

- **GitHub**: [github.com/andredeomondes](https://github.com/andredeomondes)
- **LinkedIn**: [linkedin.com/in/andredeomondes](https://linkedin.com/in/andredeomondes)

---

> *"Who's gonna carry the boats?!"* — David Goggins
>
> **Stay Hard. Never settle. Keep building.**
