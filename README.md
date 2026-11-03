
# Rental Microservices

Cette application est une démonstration de microservices en Java avec Spring Boot.
Elle contient deux services indépendants orchestrés avec Docker Compose.

---

## 🎓 Mini Tutoriel: Créer un Projet Spring Boot avec Spring Initializer

Si vous voulez créer vos propres services Spring Boot, voici comment faire :

### Étape 1: Accéder à Spring Initializer

Ouvrez votre navigateur et allez sur: **https://start.spring.io/**

### Étape 2: Configurer le Projet

Remplissez le formulaire avec:

```
Project:          Gradle Project (ou Maven Project)
Language:         Java
Spring Boot:      4.1.1 (dernière version recommandée)
Group:            com.example
Artifact:         mon-service (nom de votre service)
Name:             Mon Service
Description:      Ma première application Spring Boot
Package name:     com.example.monservice
Packaging:        Jar
Java version:     17
```

### Étape 3: Ajouter les Dépendances

Cliquez sur "ADD DEPENDENCIES" et sélectionnez:
- **Spring Web** (pour créer des APIs REST)
- **Spring Boot DevTools** (optionnel, pour le rechargement automatique)

Vous pouvez aussi ajouter:
- **Validation** (pour valider les données)
- **Lombok** (optionnel, pour réduire le code)

### Étape 4: Générer le Projet

Cliquez sur "GENERATE" (bouton bleu en bas)
- Cela télécharge un fichier `.zip`
- Dézippez-le dans votre répertoire de travail

### Étape 5: Ouvrir dans l'IDE

```bash
# Dézippez le fichier
unzip mon-service.zip

# Ouvrez le dossier dans votre IDE (IntelliJ IDEA, VS Code, etc.)
cd mon-service
```

### Étape 6: Créer Votre Premier Endpoint

Ouvrez `src/main/java/com/example/monservice/MonServiceApplication.java`:

```java
package com.example.monservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MonServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonServiceApplication.class, args);
    }
}

@RestController
class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }
}
```

### Étape 7: Lancer l'Application

```bash
# Avec Gradle
./gradlew bootRun

# Ou directement avec Maven (si Maven)
./mvnw spring-boot:run
```

L'application démarre sur `http://localhost:8080`

### Étape 8: Tester l'Endpoint

```bash
curl http://localhost:8080/hello
# Résultat: Hello from Spring Boot!
```

### 📚 Prochaines Étapes

Pour progresser, vous pouvez:
- Ajouter des contrôleurs pour gérer vos ressources
- Créer des services métier
- Ajouter une base de données (JPA)
- Implémenter une validation
- Ajouter des tests unitaires

Consultez ce projet pour voir des exemples complets dans `car-rental-service/` et `hello-service/` !

---

## Services

### 1. Car Rental Service
Service principal de gestion de location de voitures.
- Port: **8080**
- Endpoints:
  - `GET /cars` - Lister toutes les voitures
  - `GET /cars/{plateNumber}` - Obtenir une voiture par plaque
  - `PUT /cars/{plateNumber}?rent=true|false` - Louer ou rendre une voiture

### 2. Hello Service
Service simple pour tester la communication inter-services.
- Port: **8081**
- Endpoints:
  - `GET /hello` - Réponse simple "Hello"

## Prérequis

- Docker
- Docker Compose

## Démarrer les services

```bash
docker-compose up --build
```

Cela va :
1. Construire les images Docker pour les deux services
2. Démarrer les conteneurs sur les ports 8080 et 8081
3. Connecter les services sur un réseau partagé

## Vérifier que les services fonctionnent

### Car Rental Service
```bash
curl http://localhost:8080/cars
```

### Hello Service
```bash
curl http://localhost:8081/hello
```

## Arrêter les services

```bash
docker-compose down
```

## Structure du projet

```
.
├── car-rental-service/     # Service de gestion de location
│   ├── src/
│   ├── build.gradle
│   ├── Dockerfile
│   └── gradlew
├── hello-service/          # Service "Hello"
│   ├── src/
│   ├── build.gradle
│   ├── Dockerfile
│   └── gradlew
└── docker-compose.yml      # Orchestration des services
```

## Objectif pédagogique

Ce projet démontre :
- L'architecture en microservices
- L'utilisation de Docker et Docker Compose
- La containerisation multi-stage
- L'orchestration de services
- La communication inter-services sur un réseau Docker
- Les bonnes pratiques Spring Boot

## Développement local

Pour développer chaque service individuellement :

```bash
# Car Rental Service
cd car-rental-service
./gradlew bootRun

# Hello Service (dans un autre terminal)
cd hello-service
./gradlew bootRun
```
