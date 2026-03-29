# 🏋️ Stay Hard System — v1.0 CLI

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

> *"Who's gonna carry the boats?!"* — David Goggins

Sistema gamificado de tracking de hábitos em CLI (linha de comando). Primeira versão do Stay Hard System.

---

## 📋 Funcionalidades

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

## 🎮 Níveis do Jogo

| Level | Dias | Título |
|-------|------|--------|
| 1 | 0-6 | 🌱 Awakening |
| 2 | 7-14 | 🔥 Forged |
| 3 | 15-29 | ⚡ Relentless |
| 4 | 30-74 | 🛡️ Unbreakable |
| 5 | 75+ | 👑 Stay Hard |

---

## 🚀 Como Rodar

### Compilar e Executar

```bash
cd src
javac StayHardApp.java
java StayHardApp
```

### Ou execute diretamente

```bash
cd src
java StayHardApp
```

---

## 📂 Estrutura do Projeto

```
src/
├── StayHardApp.java          ← Main (ponto de entrada)
├── controller/
│   ├── UserController.java
│   └── HabitController.java
├── domain/
│   ├── entities/
│   │   ├── User.java
│   │   └── Habit.java
│   ├── enums/
│   │   ├── Priority.java
│   │   └── Status.java
│   └── utils/
│       └── ConsoleVisual.java
├── repository/
│   ├── UserRepository.java
│   ├── HabitRepository.java
│   └── csv/
│       ├── CsvUserRepository.java
│       └── CsvHabitRepository.java
├── service/
│   ├── UserService.java
│   ├── HabitService.java
│   └── LevelService.java
└── ui/
    └── UserMenus.java

data/
├── user.csv                  ← Dados do usuário
└── habits.csv                ← Hábitos salvos
```

---

## 🧪 Tecnologias

| Tecnologia | Versão |
|------------|--------|
| Java | 21 |
| Armazenamento | CSV |

---

## 📖 Conceitos Aprendidos

- POO (Encapsulamento, Herança implícita)
- Clean Code (nomes, responsabilidades)
- Separação de camadas (Controller, Service, Repository)
- Persistência com CSV
- Manipulação de Console

---

## 🔜 Próximas Versões

| Versão | Objetivo |
|--------|----------|
| [v2.0 Maven](https://github.com/andredeomondes/stay-hard-v2-maven) | Maven + JUnit |
| [v3.0 Spring](https://github.com/andredeomondes/stay-hard-v3-spring) | Spring Boot |
| [v4.0 REST](https://github.com/andredeomondes/stay-hard-v4-rest) | REST API + JWT |

---

## 👤 Autor

**André de Omondes**
- GitHub: [@andredeomondes](https://github.com/andredeomondes)
- LinkedIn: [in/andredeomondes](https://www.linkedin.com/in/andredeomondes/)

---

## 📄 License

MIT
