# 🔥 Stay Hard System — v1.0 CLI

> *Sistema gamificado de tracking de hábitos em CLI*

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Java](https://img.shields.io/badge/POO-100%25-blue?style=for-the-badge)
![CSV](https://img.shields.io/badge/Persistência-CSV-green?style=for-the-badge)

---

> *"Who's gonna carry the boats?!"* — David Goggins

**Stay Hard** é um sistema gamificado de tracking de hábitos em linha de comando. Crie hábitos, acompanhe seu progresso, evolua de nível e **permaneça hard**.

---

## ⚡ Funcionalidades

- ✅ Criar usuário com nome
- ✅ Criar hábitos (HIGH, MEDIUM, LOW priority)
- ✅ Listar hábitos por prioridade
- ✅ Marcar hábito como IN_PROGRESS
- ✅ Marcar hábito como DONE
- ✅ Resetar todos os hábitos
- ✅ Sistema de dias (completo/falhou)
- ✅ Cálculo de streak (sequência)
- ✅ Sistema de levels (Awakening → Stay Hard)
- ✅ Salvar em CSV (persistência)
- ✅ Interface visual no console

---

## 🎮 Sistema de Níveis

| Level | Dias | Título | Badge |
|-------|------|--------|-------|
| 1 | 0-6 | 🌱 Awakening | Início da jornada |
| 2 | 7-14 | 🔥 Forged | Forjado no fogo |
| 3 | 15-29 | ⚡ Relentless | Implacável |
| 4 | 30-74 | 🛡️ Unbreakable | Inquebrável |
| 5 | 75+ | 👑 Stay Hard | Mestre |

---

## 🚀 Como Rodar

### Opção 1: Compilar e Executar

```bash
cd src
javac StayHardApp.java
java StayHardApp
```

### Opção 2: Executar diretamente

```bash
cd src
java StayHardApp
```

---

## 📂 Estrutura do Projeto

```
stay-hard-v1-cli/
├── README.md              ← Você está aqui
├── .gitignore
├── src/
│   ├── StayHardApp.java          ← Main (ponto de entrada)
│   ├── controller/
│   │   ├── UserController.java
│   │   └── HabitController.java
│   ├── domain/
│   │   ├── entities/
│   │   │   ├── User.java
│   │   │   └── Habit.java
│   │   ├── enums/
│   │   │   ├── Priority.java
│   │   │   └── Status.java
│   │   └── utils/
│   │       └── ConsoleVisual.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── HabitRepository.java
│   │   └── csv/
│   │       ├── CsvUserRepository.java
│   │       └── CsvHabitRepository.java
│   ├── service/
│   │   ├── UserService.java
│   │   ├── HabitService.java
│   │   └── LevelService.java
│   └── ui/
│       └── UserMenus.java
├── data/
│   ├── user.csv                  ← Dados do usuário
│   └── habits.csv                ← Hábitos salvos
└── docs/                         ← Diários de estudo
```

---

## 🏗️ Arquitetura (MVC + Repository)

```
┌─────────────────────────────────────────────────────────────┐
│                         UI Layer                             │
│                    (UserMenus.java)                         │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                    Controller Layer                          │
│            (UserController, HabitController)                   │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                     Service Layer                            │
│           (UserService, HabitService, LevelService)            │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                   Repository Layer                           │
│              (UserRepository, HabitRepository)                 │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                     Data Layer                               │
│                      (CSV Files)                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎯 Conceitos Aprendidos

### Programação
- ✅ **POO**: Encapsulamento, Herança, Polimorfismo
- ✅ **Clean Code**: Nomes significativos, responsabilidades únicas
- ✅ **Separação de Camadas**: Controller → Service → Repository

### Java
- ✅ **Collections**: List, Set, Map
- ✅ **Streams API**: Lambda expressions
- ✅ **Enums**: Prioridades e Status
- ✅ **Exception Handling**: Try-catch
- ✅ **File I/O**: Leitura e escrita de arquivos

### Arquitetura
- ✅ **MVC**: Model-View-Controller
- ✅ **Repository Pattern**: Abstração de persistência
- ✅ **Service Layer**: Lógica de negócio

---

## 📊 Tecnologias

| Tecnologia | Versão |
|------------|--------|
| Java | 21 |
| Armazenamento | CSV |
| Build | Manual (javac) |

---

## 🔜 Próximas Versões

O sistema Stay Hard evolui progressivamente:

| Versão | Stack | Status | Link |
|--------|-------|--------|------|
| **v1-cli** | Java CLI | ✅ Completo | Este repo |
| **v2-Maven** | Java + Maven + JUnit | 📅 Futuro | [stay-hard-v2-maven](https://github.com/andredeomondes/stay-hard-v2-maven) |
| **v3-Spring** | Spring Boot | 📅 Futuro | [stay-hard-v3-spring](https://github.com/andredeomondes/stay-hard-v3-spring) |
| **v4-REST** | REST API + JWT | 📅 Futuro | [stay-hard-v4-rest](https://github.com/andredeomondes/stay-hard-v4-rest) |

---

## 📚 Repositórios Relacionados

| Repo | Descrição |
|------|-----------|
| [stay-hard-system](https://github.com/andredeomondes/stay-hard-system) | Roadmap completo (209 dias) |
| [java-study](https://github.com/andredeomondes/java-study) | Estudos Java |
| [ds-java-spring-professional](https://github.com/andredeomondes/ds-java-spring-professional) | Curso DevSuperior |
| [CadastroDeNinjas](https://github.com/andredeomondes/CadastroDeNinjas) | API Spring Boot |

---

## 🤝 Como Contribuir

1. Fork este repositório
2. Crie uma branch (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'feat: adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

---

## 👤 Autor

**André de Omondes**

- GitHub: [@andredeomondes](https://github.com/andredeomondes)
- LinkedIn: [in/andredeomondes](https://linkedin.com/in/andredeomondes/)
- Email: andre.deomondes@email.com

---

## 📄 Licença

Este projeto está sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

## 💪 Stay Hard

> *"Who's gonna carry the boats?"*

**Never quit. Never settle. Stay Hard.**
