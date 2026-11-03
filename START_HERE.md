👋 START HERE - BIENVENUE DANS LE PROJET MICROSERVICES !
═══════════════════════════════════════════════════════════════════════════

Vous venez de cloner un projet pédagogique d'architecture microservices.
Voici comment démarrer en 3 étapes simples :

📋 ÉTAPE 1 : VÉRIFIER LES PRÉREQUIS (1 min)

Assurez-vous d'avoir installé :
  ✓ Docker (https://www.docker.com/products/docker-desktop)
  ✓ Docker Compose (inclus dans Docker Desktop)

Vérifiez :
  $ docker --version
  $ docker-compose --version

Si les deux commandes affichent une version, vous êtes bon ! ✓

═══════════════════════════════════════════════════════════════════════════

🚀 ÉTAPE 2 : LANCER LES SERVICES (5 min)

Option A (Recommandée - Plus facile) :
  $ ./start.sh up

Option B (Directement) :
  $ docker-compose up --build

Attendez que les deux services se lancent. Vous verrez :
  ✓ car-rental-service started on :8080
  ✓ hello-service started on :8081

═══════════════════════════════════════════════════════════════════════════

✅ ÉTAPE 3 : VÉRIFIER QUE ÇA MARCHE (1 min)

Ouvrez un autre terminal et testez :

Test 1 - Car Rental Service :
  $ curl http://localhost:8080/cars

Vous devriez voir :
  [{"plate":"ABC123","brand":"Toyota","rented":false}, ...]

Test 2 - Hello Service :
  $ curl http://localhost:8081/hello

Vous devriez voir :
  Hello

✨ FÉLICITATIONS ! Les deux services fonctionnent ! 🎉

═══════════════════════════════════════════════════════════════════════════

📚 ENSUITE, LISEZ LA DOCUMENTATION :

Vous avez maintenant 3 options selon votre temps disponible :

  ⏱️  5 MINUTES ?
      → Lisez: QUICK_START.md
      
  ⏱️  30 MINUTES ?
      → Lisez: README.md
      → Lisez: car-rental-service/README.md
      → Lisez: hello-service/README.md
      
  ⏱️  1-2 HEURES ?
      → Lisez: INDEX.md (guide complet)
      → Lisez: MICROSERVICES.md (approfondissement)
      → Lisez: PROJECT_STRUCTURE.txt (architecture)
      → Explorez le code source

═══════════════════════════════════════════════════════════════════════════

🎓 VOUS VOULEZ APPRENDRE ?

Voici les concepts clés expliqués dans ce projet :

  1. Microservices
     → Deux services indépendants (car-rental + hello)
     
  2. Docker
     → Chaque service s'exécute dans un conteneur
     → Dockerfile multi-stage pour images légères
     
  3. Docker Compose
     → Orchestre les deux services
     → Gère le réseau entre services
     
  4. REST APIs
     → Services communiquent via HTTP
     → JSON pour les données
     
  5. Spring Boot
     → Framework Java pour les APIs
     → Configuration simple et élégante

Tout cela en UN projet simple et pédagogique ! 🚀

═══════════════════════════════════════════════════════════════════════════

💡 COMMANDES UTILES :

  # Afficher l'aide du script
  ./start.sh --help
  
  # Voir les logs en direct
  ./start.sh logs
  
  # Vérifier le statut des services
  ./start.sh ps
  
  # Tester les endpoints
  ./start.sh test
  
  # Arrêter les services
  ./start.sh down

═══════════════════════════════════════════════════════════════════════════

🔍 EXPLORER LE CODE :

Structure simple à comprendre :

  car-rental-service/src/main/java/com/example/rental/
    ├── RentalApplication.java          Point d'entrée Spring Boot
    ├── entity/Car.java                 Modèle de données
    ├── service/RentalService.java      Logique métier
    └── web/RentalWebService.java       Endpoints REST
    
  hello-service/src/main/java/com/example/hello/
    ├── HelloApplication.java           Point d'entrée Spring Boot
    └── web/HelloWebService.java        Endpoint simple

Tous les fichiers sont commentés pour vous aider ! 📝

═══════════════════════════════════════════════════════════════════════════

🎯 PROCHAINES ÉTAPES :

Une fois familiarisé avec le projet, vous pouvez :

  1️⃣  Modifier le code (p.ex. ajouter une nouvelle voiture)
  2️⃣  Ajouter une nouvelle API (p.ex. /cars/search)
  3️⃣  Ajouter une base de données (PostgreSQL)
  4️⃣  Ajouter un service de monitoring
  5️⃣  Implémenter un API Gateway

Voir MICROSERVICES.md section "Exercices pédagogiques" pour les détails !

═══════════════════════════════════════════════════════════════════════════

❓ BESOIN D'AIDE ?

Question: Comment arrêter les services ?
Réponse: Ctrl+C dans le terminal où vous avez lancé ./start.sh up
Ou:     ./start.sh down

Question: J'ai une erreur de port déjà utilisé ?
Réponse: Changez les ports dans docker-compose.yml
Ou:     8080:8080 → 8090:8080 (si 8080 est occupé)

Question: Comment développer localement sans Docker ?
Réponse: cd car-rental-service && ./gradlew bootRun

Question: Les services peuvent-ils communiquer entre eux ?
Réponse: Oui ! Ils sont sur le même réseau Docker (rental-network)

Plus de réponses: Voir INDEX.md section "Aide rapide"

═══════════════════════════════════════════════════════════════════════════

🎬 PETIT DÉFI :

Essayez ceci pour vous aider à comprendre :

  1. Lancez les services avec ./start.sh up
  2. Ouvrez un nouveau terminal
  3. Modifiez car-rental-service/src/main/.../RentalService.java
     → Ajoutez un debug print pour voir quand getAllCars() est appelée
  4. Refaites un build : cd car-rental-service && ./gradlew build
  5. Redémarrez les services
  6. Appelez l'API et vérifiez votre debug message dans les logs

C'est ça, la magie du développement ! 🪄

═══════════════════════════════════════════════════════════════════════════

📞 RESSOURCES :

  • Documentation officielle:
    - Spring Boot: https://spring.io/projects/spring-boot
    - Docker: https://docs.docker.com/
    - Docker Compose: https://docs.docker.com/compose/
    
  • Fichiers du projet:
    - README.md - Documentation principale
    - MICROSERVICES.md - Guide pédagogique détaillé
    - QUICK_START.md - Démarrage rapide
    - INDEX.md - Guide complet de navigation

═══════════════════════════════════════════════════════════════════════════

✨ BON APPRENTISSAGE ! ✨

Vous avez tout ce qu'il faut pour apprendre les microservices et Docker.

Si vous avez des questions, consultez d'abord :
  1. Cette page (que vous lisez)
  2. QUICK_START.md
  3. INDEX.md (pour naviguer la documentation)
  4. MICROSERVICES.md (pour l'approfondissement)

Le code source est simple et bien structuré.
N'hésitez pas à explorer et à modifier !

Bon apprentissage ! 🚀

═══════════════════════════════════════════════════════════════════════════

Créé avec ❤️ pour les étudiants en informatique
Microservices Rental Project v1.0
