
# Rental Microservices

Cette application est une démonstration de microservices en Java avec Spring Boot.
Elle contient deux services indépendants orchestrés avec Docker Compose.

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
