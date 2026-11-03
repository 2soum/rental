# ✅ Vérification CI/CD : Guide de test local

Ce guide vous montre comment vérifier que chaque étape de la pipeline fonctionne localement avant de la pousser sur GitHub.

## 🧪 Tester Phase 1 : Tests & Qualité

### 1.1 Unit Tests

```bash
cd car-rental-service
./gradlew clean test

# Résultat attendu:
# BUILD SUCCESSFUL
# > 40+ test methods passed
```

✅ **Status:** Tests passant localement = Les tests GitHub Actions passeront aussi

### 1.2 Build Gradle

```bash
cd car-rental-service
./gradlew build -x test

# Résultat attendu:
# BUILD SUCCESSFUL
# > car-rental-service/build/libs/rental-0.0.1-SNAPSHOT.jar
```

✅ **Status:** Build réussi = Aucune erreur de compilation

### 1.3 Vérifier les dépendances

```bash
cd car-rental-service
./gradlew dependencies

# Cherchez:
# - org.springframework.boot:spring-boot-starter-web:3.3.0
# - org.springframework.boot:spring-boot-starter-test:3.3.0
# - org.mockito:mockito-core:5.1.1
```

✅ **Status:** Tous les `spring-boot-starter-*` résolus sans erreur

---

## 🐳 Tester Phase 2 : Docker Build

### 2.1 Build l'image Docker localement

```bash
cd car-rental-service

# Build l'image
docker build -t car-rental-service:local .

# Résultat attendu:
# [+] Building 45.3s (12/12) FINISHED
# => => naming to docker.io/library/car-rental-service:local
```

✅ **Status:** Image buildée sans erreur

### 2.2 Vérifier la taille de l'image

```bash
docker images car-rental-service:local

# Résultat attendu:
# REPOSITORY                 TAG      SIZE
# car-rental-service         local    ~200MB (max 300MB)
```

✅ **Status:** Taille < 300MB = Multi-stage build OK

### 2.3 Lancer le conteneur localement

```bash
# Lancer l'image
docker run -p 8080:8080 car-rental-service:local

# Attendre le message:
# "Tomcat started on port 8080"

# En autre terminal, tester l'endpoint:
curl http://localhost:8080/cars
# Résultat attendu: JSON avec liste de voitures ou []
```

✅ **Status:** Conteneur démarre et répond aux requêtes

### 2.4 Nettoyer

```bash
# Arrêter le conteneur (Ctrl+C)
# Supprimer l'image
docker rmi car-rental-service:local
```

---

## 🔐 Tester Phase 1B : Sécurité

### 3.1 Grype (CVE Scan) - Optionnel localement

```bash
# Installer Grype (si pas fait)
curl -sSfL https://raw.githubusercontent.com/anchore/grype/main/install.sh | sh -s -- -b /usr/local/bin

# Scaner les dépendances
cd car-rental-service
grype .

# Résultat attendu:
# No vulnerabilities found
# (ou liste CVE si vulnérabilités détectées)
```

✅ **Status:** Pas de CVE critique (high/critical)
⚠️ **Status:** Si CVE trouvée = GitHub Actions bloquera aussi

### 3.2 Gitleaks (Secrets Scan) - Optionnel localement

```bash
# Installer Gitleaks
brew install gitleaks  # macOS

# Scaner le repo
cd /Users/benoitcharroux/Downloads/rent/rental
gitleaks detect

# Résultat attendu:
# 0 findings
```

✅ **Status:** Aucun secret détecté = OK

---

## 📊 Checklist complète avant push

Avant de faire `git push`, vérifiez:

```
PHASE 1 - TESTS:
  ☐ ./gradlew clean test → BUILD SUCCESSFUL
  ☐ Tous les tests passent (40+ test methods)
  
PHASE 1 - BUILD:
  ☐ ./gradlew build -x test → BUILD SUCCESSFUL
  ☐ JAR créé: build/libs/rental-0.0.1-SNAPSHOT.jar

PHASE 1 - SÉCURITÉ:
  ☐ Aucun secret commité (pas de API keys, tokens)
  ☐ Aucune dépendance vulnérable évidente
  
PHASE 2 - DOCKER:
  ☐ docker build . → réussi
  ☐ Taille < 300MB
  ☐ docker run -p 8080:8080 → démarre sans erreur
  ☐ curl http://localhost:8080/cars → répond
```

Si tout ✅, vous pouvez pousser !

---

## 🚀 Exemple workflow complet

```bash
# 1. Faire une modification
vim car-rental-service/src/main/java/com/example/rental/web/RentalWebService.java

# 2. Tester localement
cd car-rental-service
./gradlew clean test              # Tests ✅
./gradlew build -x test           # Build ✅
docker build -t test:local .      # Docker ✅
docker run -p 8080:8080 test:local
curl http://localhost:8080/cars   # API ✅
# (Ctrl+C pour arrêter)

# 3. Si tout OK, committer et pousser
git add .
git commit -m "Feature: add new endpoint"
git push origin main

# 4. Regarder GitHub Actions
# → Allez sur https://github.com/charroux/rental/actions
# → Regardez la pipeline s'exécuter automatiquement ✅

# 5. Après ~5-10 min
# ✅ Tous les tests passent
# ✅ Image Docker pushée sur GHCR
# ✅ Rapports de sécurité générés
```

---

## 🐛 Troubleshooting

### ❌ Erreur: "Gradle wrapper not found"
```bash
# Solution: Être dans le bon répertoire
cd car-rental-service
./gradlew clean test
```

### ❌ Test échoue localement
```bash
# C'est normal! Le test échouera aussi sur GitHub Actions
# Fixez le bug localement avant de pousser

./gradlew clean test --info  # Log détaillés
```

### ❌ Docker build échoue
```bash
# Vérifiez que Dockerfile existe
ls car-rental-service/Dockerfile

# Vérifiez que Docker est lancé
docker ps

# Essayez un build simple
docker build --no-cache car-rental-service/
```

### ❌ Conteneur démarre mais ne répond pas
```bash
# Vérifiez les logs du conteneur
docker logs <container_id>

# Cherchez: "Tomcat started on port 8080"
# Si erreur: "Port 8080 already in use"
docker ps  # Trouvez le container
docker stop <container_id>
```

---

## 💡 Conseils

✅ **Avant CHAQUE push:**
- Lancez `./gradlew clean test` localement
- Testez l'image Docker manuellement
- Vérifiez que vous n'avez pas commité de secrets

✅ **Pour déboguer une pipeline échec:**
- Cliquez sur le run échoué dans Actions
- Regardez le step qui échoue
- Reproduisez localement
- Fixez et repoussiez

✅ **Docker cache = builds plus rapides:**
- Premier build: ~5 min
- Builds suivants: ~2-3 min (grâce au cache des couches)

✅ **GHCR gratuit:**
- Images privées: oui (par défaut, au repos level)
- Images publiques: oui
- Limite: 0.5GB par image (largement suffisant)

---

## 📈 Prochaines étapes

Une fois confortable avec cette validation locale:

1. Ajoutez plus de tests (E2E, intégration)
2. Testez docker-compose localement
3. Ajoutez Phase 3 (Kind cluster integration)
4. Ajoutez Phase 4 (staging deployment)

Pour chaque étape, validez localement d'abord, puis ajoutez à GitHub Actions.

---

## 📞 Questions?

Consultez:
- `docs/CI-CD-PIPELINE.md` - Documentation technique complète
- `docs/CI-CD-ARCHITECTURE.md` - Architecture et flux visuels
- `docs/STUDENT-CI-CD-QUICKSTART.md` - Guide rapide
