# Guide pédagogique : Architecture Microservices

## Objectifs d'apprentissage

Ce projet démontre les concepts fondamentaux des microservices :

### 1. **Séparation des responsabilités**
   - Chaque service a une responsabilité unique
   - **car-rental-service** : gestion de location
   - **hello-service** : démonstration simple de service

### 2. **Indépendance des services**
   - Chaque service peut être déployé indépendamment
   - Chaque service a son propre build.gradle
   - Chaque service écoute sur un port différent (8080 et 8081)

### 3. **Containerisation avec Docker**
   - Chaque service a son propre Dockerfile multi-stage
   - **Build stage** : compilation dans un conteneur Gradle+JDK
   - **Runtime stage** : exécution avec JRE léger
   - Cela réduit la taille de l'image finale

### 4. **Orchestration avec Docker Compose**
   - `docker-compose.yml` orchestre les deux services
   - Les services sont connectés via un réseau Docker partagé
   - Les ports sont mappés pour l'accès externe

## Concepts clés

### Port Mapping
```yaml
car-rental-service:
  ports:
    - "8080:8080"    # localhost:8080 -> conteneur:8080

hello-service:
  ports:
    - "8081:8081"    # localhost:8081 -> conteneur:8081
```

### Réseau Docker
```yaml
networks:
  rental-network:
    driver: bridge
```
Les services peuvent s'appeler par leur nom de service :
- `http://car-rental-service:8080` depuis hello-service
- `http://hello-service:8081` depuis car-rental-service

### Dockerfile Multi-stage

**Stage 1 : Build**
```dockerfile
FROM gradle:8.14-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test
```
- Utilise une image Gradle complète
- Compile le code source

**Stage 2 : Runtime**
```dockerfile
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```
- Utilise une image JRE légère (plus petite)
- Copie uniquement le JAR compilé

**Avantage** : L'image finale n'inclut pas Gradle, réduisant la taille de ~800MB

## Exercices pédagogiques

### 1. Lancer les deux services
```bash
docker-compose up --build
```

### 2. Tester les endpoints
```bash
# Car Rental
curl http://localhost:8080/cars
curl http://localhost:8080/cars/ABC123

# Hello Service
curl http://localhost:8081/hello
```

### 3. Communication inter-services (exercice avancé)
Modifier le hello-service pour appeler le car-rental-service :

```java
@GetMapping("/hello-cars")
public ResponseEntity<String> helloCars() {
    RestTemplate restTemplate = new RestTemplate();
    List<Car> cars = restTemplate.getForObject(
        "http://car-rental-service:8080/cars",
        List.class
    );
    return ResponseEntity.ok("Hello, there are " + cars.size() + " cars!");
}
```

### 4. Ajouter une base de données
Modifier le car-rental-service pour utiliser PostgreSQL :
- Ajouter le driver PostgreSQL dans `build.gradle`
- Configurer la connexion dans `application.properties`
- Ajouter un service PostgreSQL dans `docker-compose.yml`

### 5. Ajouter des logs centralisés
Configurer ELK Stack (Elasticsearch, Logstash, Kibana) dans docker-compose.yml

## Points d'amélioration pour les étudiants

1. **Persistance** : Remplacer la mémoire par une base de données
2. **API Gateway** : Ajouter un service proxy pour les deux services
3. **Service Discovery** : Implémenter Eureka pour la découverte dynamique
4. **Circuit Breaker** : Ajouter Resilience4j pour la résilience
5. **Configuration centralisée** : Ajouter Spring Cloud Config
6. **Monitoring** : Ajouter Prometheus et Grafana
7. **Tracing distribué** : Ajouter Jaeger ou Zipkin

## Ressources utiles

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Microservices Patterns](https://microservices.io/)
- [12 Factor App](https://12factor.net/)
