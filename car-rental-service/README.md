
# Car Rental Service

Service REST en Java avec Spring Boot pour gérer une flotte de voitures en location.

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

L'application démarre sur `http://localhost:8080`

## Créer une image Docker

Depuis le répertoire `car-rental-service`, exécutez :

```bash
docker build -t car-rental-service .
```

## Lancer le conteneur

```bash
docker run --rm -p 8080:8080 car-rental-service
```

- `--rm` : supprime automatiquement le conteneur quand il s'arrête
- `-p 8080:8080` : expose le port 8080
- `car-rental-service` : nom de l'image

## API Endpoints

### Lister toutes les voitures

```bash
curl http://localhost:8080/cars
```

Réponse :
```json
[
  {"plate":"ABC123","brand":"Toyota","rented":false},
  {"plate":"XYZ789","brand":"Honda","rented":false},
  {"plate":"DEF456","brand":"BMW","rented":false}
]
```

### Obtenir une voiture par plaque

```bash
curl http://localhost:8080/cars/ABC123
```

### Louer une voiture

```bash
curl -X PUT "http://localhost:8080/cars/ABC123?rent=true" \
  -H "Content-Type: application/json" \
  -d '{}'
```

### Rendre une voiture

```bash
curl -X PUT "http://localhost:8080/cars/ABC123?rent=false" \
  -H "Content-Type: application/json" \
  -d '{}'
```

## Architecture

- `entity/Car.java` - Modèle de données
- `service/RentalService.java` - Logique métier
- `web/RentalWebService.java` - Endpoints REST
- `RentalApplication.java` - Point d'entrée Spring Boot

## Remarques

- L'état est en mémoire, sans persistance en base de données
- Cet exemple est volontairement simple et pédagogique
- Les données sont réinitialisées à chaque démarrage
