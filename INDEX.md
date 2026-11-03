📚 GUIDE DE DOCUMENTATION - Microservices Rental
═══════════════════════════════════════════════════════════════════════════

Bienvenue ! Voici votre guide pour naviguer dans la documentation du projet.

🎯 PAR OBJECTIF :

┌─ Je veux démarrer rapidement
│  └─ 👉 Lisez : QUICK_START.md (5 min)
│     • Prérequis minimaux
│     • 3 étapes pour démarrer
│     • Commandes essentielles
│     • Dépannage rapide

├─ Je veux comprendre l'architecture
│  └─ 👉 Lisez : README.md (10 min)
│     • Vue d'ensemble des deux services
│     • Comment démarrer
│     • Endpoints documentés
│     • Structure générale

├─ Je veux apprendre les concepts
│  └─ 👉 Lisez : MICROSERVICES.md (20 min)
│     • Concepts microservices expliqués
│     • Multi-stage Docker détaillé
│     • Orchestration Docker Compose
│     • Exercices d'apprentissage

├─ Je veux voir toute la structure
│  └─ 👉 Lisez : PROJECT_STRUCTURE.txt (15 min)
│     • Arborescence complète
│     • Description de chaque fichier
│     • Flux de build et run
│     • Dépendances et configuration

└─ Je veux un résumé rapide
   └─ 👉 Lisez : PROJECT_SUMMARY.txt (10 min)
      • Vue d'ensemble structurée
      • Services et endpoints
      • Concepts clés
      • Points d'amélioration

═══════════════════════════════════════════════════════════════════════════

📖 PAR FICHIER :

┌─ README.md
│  Type: Documentation principale
│  Public: Tous
│  Contenu:
│  • Présentation du projet
│  • Services (car-rental + hello)
│  • Lancer avec Docker Compose
│  • Endpoints API
│  • Structure du projet
│  • Développement local
│  Temps de lecture: ~10 min
│  Action: Lisez d'abord !

├─ QUICK_START.md
│  Type: Guide de démarrage
│  Public: Étudiants
│  Contenu:
│  • Installation en 3 étapes
│  • Commandes essentielles
│  • Endpoints avec exemples curl
│  • Résultats attendus
│  • Développement sans Docker
│  • Dépannage
│  Temps de lecture: ~5 min
│  Action: Lisez si vous êtes pressé !

├─ MICROSERVICES.md
│  Type: Guide pédagogique
│  Public: Étudiants + Formateurs
│  Contenu:
│  • Objectifs d'apprentissage
│  • Concepts clés expliqués
│  • Port mapping détaillé
│  • Réseau Docker
│  • Dockerfile multi-stage décortiqué
│  • Exercices progressifs
│  • Ressources d'apprentissage
│  Temps de lecture: ~20 min
│  Action: Lisez pour comprendre en profondeur !

├─ PROJECT_STRUCTURE.txt
│  Type: Référence technique
│  Public: Développeurs
│  Contenu:
│  • Arborescence avec emojis
│  • Description de chaque fichier
│  • Flux de build et run
│  • Dépendances Gradle
│  • Configuration Spring Boot
│  • Bénéfices éducatifs
│  Temps de lecture: ~15 min
│  Action: Consultez comme référence !

├─ PROJECT_SUMMARY.txt
│  Type: Vue d'ensemble
│  Public: Management + Étudiants
│  Contenu:
│  • Résumé structuré
│  • Services et endpoints
│  • Docker et Compose
│  • Points clés
│  • Concepts enseignés
│  Temps de lecture: ~10 min
│  Action: Imprimez pour un poster !

├─ docker-compose.yml
│  Type: Configuration
│  Public: Développeurs
│  Contenu:
│  • Services: car-rental + hello
│  • Ports: 8080 et 8081
│  • Build multi-stage
│  • Réseau partagé
│  • Variables d'environnement
│  Action: Exécutez avec `docker-compose up --build`

├─ start.sh
│  Type: Script utilitaire
│  Public: Tous
│  Contenu:
│  • Commandes interactives
│  • Interface en couleurs
│  • Aide intégrée
│  Commandes:
│  • ./start.sh up       (démarrer)
│  • ./start.sh down     (arrêter)
│  • ./start.sh logs     (logs)
│  • ./start.sh test     (tester endpoints)
│  • ./start.sh ps       (status)
│  Action: Exécutez : `./start.sh --help`

└─ .gitignore
   Type: Configuration Git
   Public: Git
   Contenu:
   • Ignore les fichiers générés
   • .gradle, build/, *.class, etc.
   Action: N'éditez pas !

═══════════════════════════════════════════════════════════════════════════

📂 DOCUMENTATIONS PAR SERVICE :

car-rental-service/README.md
├─ Description du service
├─ Prérequis
├─ Construire localement
├─ Lancer avec Docker
├─ Endpoints API
├─ Architecture
└─ Remarques pédagogiques
Lecture: ~5 min

hello-service/README.md
├─ Description du service
├─ Prérequis
├─ Construire localement
├─ Lancer avec Docker
├─ Endpoints API
├─ Architecture
└─ Remarques pédagogiques
Lecture: ~3 min

═══════════════════════════════════════════════════════════════════════════

🚀 CHEMIN RECOMMANDÉ POUR DÉMARRER :

1️⃣  RAPIDE (10 min)
    └─ QUICK_START.md
       → ./start.sh up
       → curl http://localhost:8080/cars
       → Fini !

2️⃣  MOYEN (30 min)
    └─ README.md
       → Comprendre les concepts
       → docker-compose up --build
       → Tester les endpoints
       → Lire car-rental-service/README.md
       → Lire hello-service/README.md

3️⃣  COMPLET (1-2 heures)
    └─ MICROSERVICES.md
       → PROJECT_STRUCTURE.txt
       → Lire le code source
       → Faire les exercices pédagogiques
       → Ajouter des features

═══════════════════════════════════════════════════════════════════════════

🎓 EXERCICES PÉDAGOGIQUES :

Niveau 1 (Débutant - 30 min):
└─ Démarrer les services avec docker-compose
└─ Appeler les endpoints avec curl
└─ Voir les logs

Niveau 2 (Intermédiaire - 1h):
└─ Modifier le hello-service pour retourner du JSON
└─ Ajouter une nouvelle voiture au car-rental-service
└─ Vérifier la communication inter-services

Niveau 3 (Avancé - 2h+):
└─ Ajouter une base de données (PostgreSQL)
└─ Implémenter une vraie logique de réservation
└─ Ajouter un API Gateway
└─ Configurer un monitoring (Prometheus + Grafana)

Voir MICROSERVICES.md pour plus de détails !

═══════════════════════════════════════════════════════════════════════════

💡 CONSEIL POUR LES FORMATEURS :

Pour une session de 3 heures:
├─ 30 min: QUICK_START.md + démo live
├─ 45 min: MICROSERVICES.md + explication du code
├─ 30 min: Exercice niveau 1 (les étudiants font)
├─ 30 min: PROJECT_STRUCTURE.txt + architecture
├─ 30 min: Exercices niveau 2 (guidés)
└─ 15 min: Q&A + ressources d'apprentissage

═══════════════════════════════════════════════════════════════════════════

📞 AIDE RAPIDE :

Question: Comment démarrer ?
Réponse: ./start.sh up

Question: Comment tester les services ?
Réponse: curl http://localhost:8080/cars

Question: Comment arrêter ?
Réponse: ./start.sh down ou Ctrl+C

Question: Où lire la documentation ?
Réponse: QUICK_START.md en premier !

Question: Comment développer localement ?
Réponse: cd car-rental-service && ./gradlew bootRun

═══════════════════════════════════════════════════════════════════════════

Bon apprentissage ! 🎓
Si vous avez des questions, consultez d'abord MICROSERVICES.md section "Ressources utiles"
