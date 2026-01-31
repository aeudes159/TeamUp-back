# TeamUp Back

API REST Spring Boot pour l'application TeamUp - gestion de groupes, messages et reactions.

## Stack Technique

- **Framework**: Spring Boot 3.x
- **Langage**: Kotlin
- **Base de donnees**: PostgreSQL (Supabase)
- **ORM**: Hibernate / JPA
- **Migrations**: Flyway
- **Build**: Gradle (Kotlin DSL)

## Fonctionnalites

- Gestion des utilisateurs
- Gestion des groupes et membres
- Discussions et messages
- Reactions aux messages (emojis)
- Feed d'activite
- Propositions et ratings de lieux

## Prerequisites

- JDK 17 ou superieur
- PostgreSQL (ou Supabase)
- Gradle 8.x (ou utiliser le wrapper `./gradlew`)

## Installation

1. Clonez le repository :
```bash
git clone <url-du-repo>
cd TeamUp-back
```

2. Configuration des variables d'environnement :

Creez un fichier `.env` ou configurez les variables systeme :

```env
# Base de donnees PostgreSQL
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:54322/postgres
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres

# Port du serveur (optionnel, defaut: 8080)
PORT=8080
```

### Configuration Supabase

Si vous utilisez Supabase comme base de donnees :

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://db.xxxxx.supabase.co:5432/postgres
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your-supabase-db-password
```

Trouvez ces informations dans : Supabase Dashboard > Project Settings > Database > Connection string (JDBC)

## Variables d'Environnement

| Variable | Requis | Defaut | Description |
|----------|--------|--------|-------------|
| `SPRING_DATASOURCE_URL` | Oui | `jdbc:postgresql://localhost:54322/postgres` | URL JDBC de la base de donnees |
| `SPRING_DATASOURCE_USERNAME` | Oui | `postgres` | Utilisateur de la base de donnees |
| `SPRING_DATASOURCE_PASSWORD` | Oui | `postgres` | Mot de passe de la base de donnees |
| `PORT` | Non | `8080` | Port du serveur HTTP |

## Lancement

### Mode Developpement

```bash
./gradlew bootRun
```

Le serveur demarre sur `http://localhost:8080`.

### Avec variables d'environnement

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/teamup \
SPRING_DATASOURCE_USERNAME=myuser \
SPRING_DATASOURCE_PASSWORD=mypassword \
./gradlew bootRun
```

## Build

### Creer le JAR

```bash
./gradlew build
```

Le JAR sera genere dans `build/libs/`.

### Executer le JAR

```bash
java -jar build/libs/teamup-back-*.jar
```

### Docker

```bash
# Build l'image
docker build -t teamup-back .

# Executer le conteneur
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/postgres \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  teamup-back
```

## API Endpoints

### Users
| Methode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/users` | Liste des utilisateurs |
| GET | `/api/users/{id}` | Obtenir un utilisateur |
| POST | `/api/users` | Creer un utilisateur |
| PUT | `/api/users/{id}` | Modifier un utilisateur |
| DELETE | `/api/users/{id}` | Supprimer un utilisateur |

### Groups
| Methode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/groups` | Liste des groupes |
| GET | `/api/groups/{id}` | Obtenir un groupe |
| POST | `/api/groups` | Creer un groupe |
| PUT | `/api/groups/{id}` | Modifier un groupe |
| DELETE | `/api/groups/{id}` | Supprimer un groupe |

### Messages
| Methode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/messages` | Liste des messages |
| GET | `/api/messages/{id}` | Obtenir un message |
| GET | `/api/messages/by-discussion/{discussionId}` | Messages d'une discussion |
| POST | `/api/messages` | Envoyer un message |
| PUT | `/api/messages/{id}` | Modifier un message |
| DELETE | `/api/messages/{id}` | Supprimer un message |

### Reactions
| Methode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/reactions` | Liste des reactions |
| GET | `/api/reactions/{id}` | Obtenir une reaction |
| GET | `/api/reactions/by-message/{messageId}` | Reactions d'un message |
| GET | `/api/reactions/by-user/{userId}` | Reactions d'un utilisateur |
| POST | `/api/reactions` | Ajouter une reaction |
| DELETE | `/api/reactions/{id}` | Supprimer une reaction |

### Discussions
| Methode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/discussions` | Liste des discussions |
| GET | `/api/discussions/by-group/{groupId}` | Discussions d'un groupe |
| POST | `/api/discussions` | Creer une discussion |

## Structure du Projet

```
TeamUp-back/
├── src/main/kotlin/main/java/io/takima/teamupback/
│   ├── user/              # Entite User
│   ├── group/             # Entite Group
│   ├── message/           # Entite Message
│   ├── reaction/          # Entite Reaction
│   ├── discussion/        # Entite Discussion
│   ├── post/              # Entite Post
│   ├── location/          # Entite Location
│   ├── rating/            # Entite Rating
│   ├── proposal/          # Entite Proposal
│   ├── groupMember/       # Entite GroupMember
│   ├── activityFeed/      # Entite ActivityFeed
│   └── common/            # Exceptions et utilitaires
├── src/main/resources/
│   ├── application.yml    # Configuration Spring
│   └── migrations/        # Scripts Flyway
│       ├── V1_0__create_primary_table.sql
│       ├── V1_1__add_location_history.sql
│       ├── V1_2__add_reaction_table.sql
│       └── V1_3__add_supabase_storage_policies.sql
├── build.gradle.kts       # Configuration Gradle
└── Dockerfile             # Configuration Docker
```

## Migrations Flyway

Les migrations sont executees automatiquement au demarrage.

| Version | Description |
|---------|-------------|
| V1_0 | Tables principales (user, group, message, etc.) |
| V1_1 | Historique des lieux par groupe |
| V1_2 | Table des reactions |
| V1_3 | Policies Supabase Storage |

### Executer les migrations manuellement

```bash
./gradlew flywayMigrate
```

## Exemples d'utilisation API

### Creer un message

```bash
curl -X POST http://localhost:8080/api/messages \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Hello World!",
    "senderId": 1,
    "discussionId": 1
  }'
```

### Ajouter une reaction

```bash
curl -X POST http://localhost:8080/api/reactions \
  -H "Content-Type: application/json" \
  -d '{
    "emoji": "👍",
    "userId": 1,
    "messageId": 1
  }'
```

### Obtenir les reactions d'un message

```bash
curl http://localhost:8080/api/reactions/by-message/1
```

## Troubleshooting

### Erreur de connexion a la base de donnees

Verifiez que :
1. PostgreSQL est demarre
2. Les credentials sont corrects
3. L'URL JDBC est valide

### Erreur Flyway "Schema validation failed"

La base de donnees ne correspond pas aux migrations. Options :
1. Reinitialiser la base de donnees
2. Ajouter `spring.jpa.hibernate.ddl-auto=update` temporairement

### Port deja utilise

```bash
# Trouver le processus utilisant le port
lsof -i :8080

# Utiliser un autre port
PORT=8081 ./gradlew bootRun
```

## Licence

Projet prive - Tous droits reserves
