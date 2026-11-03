
# Hello Service

Service REST simple en Java avec Spring Boot qui répond "Hello" sur `/hello`.

## Prérequis

- Java 17
- Gradle
- Docker (optionnel, si vous utilisez docker-compose depuis la racine)

## Construire l'application

Pour compiler le projet localement :

```bash
./gradlew build
```

## Lancer localement

```bash
./gradlew bootRun
```

L'application démarre sur `http://localhost:8081`

## Créer une image Docker

Depuis le répertoire `hello-service`, exécutez :

```bash
docker build -t hello-service .
```

## Lancer le conteneur

```bash
docker run --rm -p 8081:8081 hello-service
```

- `--rm` : supprime automatiquement le conteneur quand il s'arrête
- `-p 8081:8081` : expose le port 8081
- `hello-service` : nom de l'image

## API Endpoints

### Obtenir le message Hello

```bash
curl http://localhost:8081/hello
```

Réponse :
```
Hello
```

## Architecture

- `web/HelloWebService.java` - Endpoint REST simple
- `HelloApplication.java` - Point d'entrée Spring Boot

## Dockerfile Multi-stage

Ce service utilise le même pattern Dockerfile multi-stage que le service Car Rental :
- **Build stage** : compilation avec Gradle et JDK 17
- **Runtime stage** : exécution avec JRE 17 (plus léger)

## Remarques

- Service léger et minimaliste pour la démonstration
- Idéal pour tester la communication inter-services
- Éducatif pour comprendre les microservices
