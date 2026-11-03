# 🚀 CI/CD Pipeline Architecture

## Vue d'ensemble visuelle

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        GIT PUSH / PULL REQUEST                          │
│                      (main, develop, feature/...)                       │
└──────────────────────────────────┬──────────────────────────────────────┘
                                   │
                    ┌──────────────┴──────────────┐
                    │                             │
        ┌───────────▼────────────┐    ┌──────────▼──────────────┐
        │   car-rental-service   │    │   hello-service        │
        │   CI/CD Pipeline       │    │   CI/CD Pipeline       │
        └───────────┬────────────┘    └──────────┬──────────────┘
                    │                             │
       ┌────────────┴─────────────┬──────────────┴────────────┐
       │                          │                           │
       ▼ PHASE 1                  ▼                           ▼
    TESTS & QUALITY          BUILD & PUSH            PIPELINE STATUS
    ═══════════════          ════════════            ══════════════════
       │                          │                           │
       ├─ JUnit Tests             ├─ Docker Build             ├─ Aggregates results
       │  (MockMVC, Mockito)      │  (Multi-stage)            │
       │                          │  (Cache optimization)     ├─ All passed? ✅
       ├─ Build Gradle            │                           │  else ❌
       │  (Compile, resolve)      ├─ Push to GHCR             │
       │                          │  (Automatic tagging)      └─ Notify developer
       ├─ CVE Scan (Grype)        │
       │  (Dependencies)          └─ Trivy Scan
       │                            (Image security)
       └─ Secrets (Gitleaks)


┌─────────────────────────────────────────────────────────────────────────┐
│                         GITHUB CONTAINER REGISTRY                       │
│                                                                          │
│  ghcr.io/charroux/rental/car-rental-service:latest                     │
│  ghcr.io/charroux/rental/car-rental-service:main-[sha]                 │
│  ghcr.io/charroux/rental/hello-service:latest                          │
│                                                                          │
│  (Available for Phase 3 & 4: testing, staging, production)             │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Détail de chaque phase

### PHASE 1A : Tests JUnit
```yaml
Name: Unit & Integration Tests
Runs on: Ubuntu Latest (GitHub Runner)

Steps:
  1. Checkout code
  2. Setup JDK 17 (Corretto)
  3. Run: ./gradlew clean test
  4. Upload results → GitHub Artifacts
  5. Publish reports → GitHub UI
```

**Fichiers testés :**
```
car-rental-service/src/test/java/
├── com/example/rental/
│   ├── web/RentalWebServiceTest.java (14 tests)
│   ├── service/RentalServiceTest.java (14 tests)
│   └── entity/CarTest.java (12 tests)
```

### PHASE 1B : Build & Code Quality
```yaml
Name: Code Quality & Security Scans
Runs on: Ubuntu Latest

Steps:
  1. Checkout code
  2. Setup JDK 17
  3. Run: ./gradlew build -x test
  4. Grype scan (CVE dependencies)
  5. Gitleaks scan (secrets detection)
  6. Upload SARIF to GitHub Security
```

### PHASE 2 : Docker Build & Push
```yaml
Name: Build & Push Docker Image
Runs on: Ubuntu Latest
Needs: [test, code-quality] (dependencies)

Steps:
  1. Setup Docker Buildx
  2. Login to GHCR (using GITHUB_TOKEN)
  3. Build image from Dockerfile
     - Multi-stage: gradle:8.14-jdk17 → eclipse-temurin:17-jre
     - Cache optimization
  4. Tag image automatically
  5. Push to GHCR (only on main branch)
  6. Trivy scan image
  7. Upload security report
```

**Tags générés automatiquement :**
```
ghcr.io/charroux/rental/car-rental-service:latest      (main only)
ghcr.io/charroux/rental/car-rental-service:main-abc123 (all commits)
```

---

## 🔄 Flux de déclenchement

### Scénario 1 : Push sur main
```bash
$ git commit -m "Add new feature"
$ git push origin main
```
✅ Tests exécutés  
✅ Sécurité scannée  
✅ Image Docker buildée  
✅ Image pushée sur GHCR  

### Scénario 2 : Pull Request
```bash
$ git checkout -b feature/xyz
$ git commit -m "WIP"
$ git push origin feature/xyz
$ gh pr create
```
✅ Tests exécutés  
✅ Sécurité scannée  
❌ Image NOT pushée (test mode)  

### Scénario 3 : Push sur hello-service uniquement
```bash
$ git commit hello-service/...
$ git push origin main
```
✅ hello-service pipeline runs  
❌ car-rental-service pipeline skipped (paths filtering)  

---

## 📊 Temps d'exécution estimé

| Phase | Durée | Notes |
|-------|-------|-------|
| Tests | 1-2 min | Cached gradle, parallel tests |
| Code Quality | 1-2 min | CVE scan + Gitleaks |
| Docker Build | 2-5 min | First time slower (no cache) |
| Trivy Scan | 1-2 min | Fast image scan |
| **Total** | **5-11 min** | Parallel jobs = faster |

**Note :** Après le premier build, Docker cache = builds 50% plus rapides

---

## 🔐 Sécurité & Authentification

### GitHub Token (GITHUB_TOKEN)
```yaml
✅ Fourni automatiquement par GitHub Actions
✅ Limité au repository courant
✅ Expiré à la fin du workflow
✅ Permissions : read code, write artifacts, push to GHCR
```

### Aucune configuration manuelle requise !
- Pas de secrets à générer
- Pas de tokens à stocker
- Authentification automatique

---

## 📈 Métriques & Observabilité

### Où consulter les résultats
```
GitHub Repository
  ↓
  Actions (tab)
    ↓
    CI/CD - car-rental-service (run)
      ├── test ────────────────── ✅
      ├── code-quality ────────── ✅
      ├── build-and-push ─────── ✅
      └── pipeline-status ────── ✅ All passed
```

### Rapports générés
```
✅ Test Reports
   └── car-rental-service/build/test-results/

✅ Security Reports
   ├── Grype (CVE dependencies) → GitHub Security
   ├── Gitleaks (secrets) → GitHub Security
   └── Trivy (image CVE) → GitHub Security

✅ Docker Image
   └── ghcr.io/charroux/rental/car-rental-service:latest
       ├── Image digest
       ├── Layer sizes
       └── Pull command
```

---

## 🚀 Évolution future

### Phase 3 (à venir)
```yaml
- Deploy to Kind cluster
- Run docker-compose integration tests
- Test inter-service communication
- E2E tests (Playwright)
```

### Phase 4 (à venir)
```yaml
- Deploy to staging environment
- Smoke tests
- Performance tests
- Manual approval gate
- Production deployment (Blue-Green)
```

---

## 📚 Architecture des fichiers

```
.github/
├── workflows/
│   ├── ci-car-rental.yml ──── Car rental service pipeline
│   └── ci-hello.yml ────────── Hello service pipeline
│
docs/
├── CI-CD-PIPELINE.md ────────── Documentation complète
└── STUDENT-CI-CD-QUICKSTART.md  Guide rapide pour étudiants
```

---

## 💡 Points clés à retenir

1. **Automatisation** : Aucun calcul manuel, tout est auto
2. **Tests first** : Code testé avant d'être pushé
3. **Sécurité** : CVE + secrets + image scan
4. **Reproducibilité** : Docker build = même résultat partout
5. **Traçabilité** : Historique complet dans GitHub
6. **Scalabilité** : Facile d'ajouter docker-compose, K8s, staging

---

## 🎓 Bénéfices pédagogiques

Pour les étudiants, cette pipeline enseigne :

✅ Automatisation des tests  
✅ CI/CD concepts réels  
✅ Docker containerization  
✅ Sécurité des dépendances  
✅ Git workflows professionnels  
✅ Infrastructure as Code (GitHub Actions)  
✅ Monitoring et observabilité  
✅ DevOps mindset  

---

## ⚙️ Détail technique

### Dockerfile Multi-stage (existant)
```dockerfile
# Stage 1: Build avec gradle
FROM gradle:8.14-jdk17 as builder
WORKDIR /app
COPY . .
RUN gradle build -x test

# Stage 2: Runtime léger
FROM eclipse-temurin:17-jre
COPY --from=builder /app/build/libs/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### GitHub Actions Workflow Pattern
```yaml
on: push/pull_request
jobs:
  test:         # Job 1: Runs immediately
    runs-on: ubuntu-latest
  code-quality: # Job 2: Runs immediately (parallel)
    runs-on: ubuntu-latest
  build-and-push: # Job 3: Waits for 1 & 2
    needs: [test, code-quality]
    runs-on: ubuntu-latest
  pipeline-status: # Job 4: Waits for all
    needs: [test, code-quality, build-and-push]
    if: always()  # Run même si erreur
```

Résultat : **Exécution optimisée, feedback rapide** ⚡

---

## 📞 Support & Troubleshooting

Pour toute issue :
1. Consultez les logs détaillés dans Actions
2. Reproduisez localement (`./gradlew clean test`)
3. Vérifiez les rapports de sécurité (Security tab)
4. Lisez `docs/CI-CD-PIPELINE.md` pour plus de détails
