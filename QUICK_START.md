# 🚀 Démarrage Rapide - Microservices Rental

## Installation et lancement (3 étapes)

### 1️⃣ Prérequis
- Docker installé
- Docker Compose installé
- Port 8080 et 8081 disponibles

### 2️⃣ Lancer les services

**Option A : Avec le script (recommandé)**
```bash
./start.sh up
```

**Option B : Avec docker-compose directement**
```bash
docker-compose up --build
```

### 3️⃣ Vérifier que ça marche

```bash
# Service de location de voitures
curl http://localhost:8080/cars

# Service Hello
curl http://localhost:8081/hello
```

## Commandes utiles

| Commande | Description |
|----------|-------------|
| `./start.sh up` | Démarrer les services |
| `./start.sh down` | Arrêter les services |
| `./start.sh logs` | Voir les logs |
| `./start.sh test` | Tester les endpoints |
| `./start.sh ps` | Status des services |

## API Endpoints

### 🚗 Car-Rental Service (Port 8080)

```bash
# Lister toutes les voitures
curl http://localhost:8080/cars

# Obtenir une voiture spécifique
curl http://localhost:8080/cars/ABC123

# Louer une voiture
curl -X PUT "http://localhost:8080/cars/ABC123?rent=true" \
  -H "Content-Type: application/json" \
  -d '{}'

# Rendre une voiture
curl -X PUT "http://localhost:8080/cars/ABC123?rent=false" \
  -H "Content-Type: application/json" \
  -d '{}'
```

### 👋 Hello Service (Port 8081)

```bash
# Obtenir un message Hello
curl http://localhost:8081/hello
```

## Résultat attendu

```bash
$ curl http://localhost:8080/cars
[
  {"plate":"ABC123","brand":"Toyota","rented":false},
  {"plate":"XYZ789","brand":"Honda","rented":false},
  {"plate":"DEF456","brand":"BMW","rented":false}
]

$ curl http://localhost:8081/hello
Hello
```

## Développement local (sans Docker)

### Car-Rental Service
```bash
cd car-rental-service
./gradlew bootRun
# Démarre sur http://localhost:8080
```

### Hello Service
```bash
cd hello-service
./gradlew bootRun
# Démarre sur http://localhost:8081
```

## Arrêter les services

```bash
# Avec le script
./start.sh down

# Avec docker-compose
docker-compose down
```

## Structure du projet

```
rental/
├── car-rental-service/    # Service 1: Gestion de location
├── hello-service/         # Service 2: Endpoint "Hello" simple
├── docker-compose.yml     # Orchestration
├── start.sh              # Script de démarrage
└── README.md             # Documentation complète
```

## Pour en savoir plus

- [README.md](./README.md) - Documentation complète
- [MICROSERVICES.md](./MICROSERVICES.md) - Guide pédagogique approfondi
- [car-rental-service/README.md](./car-rental-service/README.md)
- [hello-service/README.md](./hello-service/README.md)

## Dépannage

### Les services ne démarrent pas
```bash
# Vérifier les logs
docker-compose logs

# Nettoyer et réessayer
./start.sh clean
./start.sh up
```

### Port 8080/8081 déjà utilisé
```bash
# Éditer docker-compose.yml pour changer les ports
# Changer "8080:8080" en "8090:8080" par exemple
```

### Erreur lors du build
```bash
# Forcer une récompilation complète
docker-compose build --no-cache
docker-compose up
```

## Points clés d'apprentissage

✅ Architecture microservices  
✅ Containerisation Docker  
✅ Orchestration avec Docker Compose  
✅ RESTful APIs avec Spring Boot  
✅ Communication inter-services  

Bon apprentissage! 🎓
