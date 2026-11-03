# CI/CD Pipeline Documentation

Ce projet utilise **GitHub Actions** pour automatiser les tests, la construction et le déploiement des microservices.

## 📋 Vue d'ensemble

Deux pipelines CI/CD dédiées :
- **ci-car-rental.yml** : Pipeline pour `car-rental-service`
- **ci-hello.yml** : Pipeline pour `hello-service`

Chaque pipeline exécute les étapes suivantes :

### **Phase 1 : Tests & Vérification de Qualité**

#### 1.1 Tests unitaires et d'intégration
```bash
./gradlew clean test
```
- Exécute tous les tests JUnit 5 avec Mockito
- Les résultats sont uploadés comme artefacts
- Les rapports de test sont publiés sur GitHub

#### 1.2 Build du projet
```bash
./gradlew build -x test
```
- Compile le projet
- Résout les dépendances
- Valide la structure Gradle

#### 1.3 Scan CVE (Grype)
```bash
grype [service-directory]
```
- Scanne les dépendances Java pour les vulnérabilités connues
- Génère un rapport SARIF uploadé aux GitHub Security Advisories
- Bloque la pipeline si des CVE critiques sont trouvées

#### 1.4 Détection de secrets (Gitleaks)
```bash
gitleaks detect
```
- Vérifie qu'aucun secret (tokens, clés API) n'est commité
- Analyse l'historique git

### **Phase 2 : Construction & Déploiement d'Image Docker**

#### 2.1 Build de l'image Docker
```dockerfile
# Multi-stage build
Stage 1: gradle:8.14-jdk17 (compilation)
Stage 2: eclipse-temurin:17-jre (runtime)
```
- Utilise le Dockerfile multi-stage existant
- Cache les couches Docker pour optimiser les builds ultérieurs
- Réduit la taille finale de l'image (~200MB au lieu de ~1GB)

#### 2.2 Push sur GitHub Container Registry (GHCR)
```
ghcr.io/charroux/rental/car-rental-service:latest
ghcr.io/charroux/rental/car-rental-service:main-[sha]
ghcr.io/charroux/rental/hello-service:latest
```

Tags générés automatiquement :
- `latest` : Tag du branch par défaut (main)
- `main-[sha]` : SHA du commit
- Semver si vous créez des releases

#### 2.3 Scan d'image Docker (Trivy)
```bash
trivy image ghcr.io/charroux/rental/car-rental-service:latest
```
- Scanne les CVE dans la couche runtime
- Analyse les fichiers de configuration
- Génère un rapport de sécurité

---

## 🚀 Configuration initiale

### Étape 1 : Aucune configuration requise ! 🎉

GitHub Actions est déjà intégré et utilise `GITHUB_TOKEN` automatiquement pour :
- Accéder à votre code
- Pousser des images sur GHCR
- Générer les rapports de sécurité

### Étape 2 : Vérifier les permissions du repository

1. Allez dans **Settings** → **Actions** → **General**
2. Assurez-vous que :
   - "Allow GitHub Actions to create and approve pull requests" ✅
   - "Read repository contents permission" ✅

### Étape 3 (Optionnel) : Configurer Docker Hub

Si vous préférez Docker Hub à la place de GHCR :

1. Créez un compte [Docker Hub](https://hub.docker.com)
2. Générez un token d'accès : Profile → Account Settings → Security → New Access Token
3. Ajoutez les secrets GitHub :
   - **Settings** → **Secrets and variables** → **Actions** → **New repository secret**
   - `DOCKER_USERNAME` : votre username Docker Hub
   - `DOCKER_TOKEN` : votre token

4. Modifiez le workflow pour utiliser Docker Hub :
```yaml
- name: Log in to Docker Hub
  uses: docker/login-action@v3
  with:
    username: ${{ secrets.DOCKER_USERNAME }}
    password: ${{ secrets.DOCKER_TOKEN }}

- name: Build and push
  uses: docker/build-push-action@v5
  with:
    context: car-rental-service
    push: true
    tags: ${{ secrets.DOCKER_USERNAME }}/car-rental-service:latest
```

---

## 📊 Déclenchement des pipelines

Les pipelines se déclenchent automatiquement sur :

### ✅ Push sur `main` ou `develop`
```bash
git push origin main
```
- Tests complets
- Scan de sécurité
- **Push de l'image sur GHCR**

### ✅ Pull Request
```bash
git push origin feature-branch
gh pr create
```
- Tests complets
- Scan de sécurité
- **L'image n'est PAS pushée** (mode test uniquement)

### ✅ Changements spécifiques au service
Les pipelines ne s'exécutent que si les fichiers du service changent :
```yaml
paths:
  - 'car-rental-service/**'
  - '.github/workflows/ci-car-rental.yml'
```

---

## 🔍 Consulter les résultats

### Dans GitHub
1. Allez à **Actions**
2. Sélectionnez le workflow (CI/CD - car-rental-service ou CI/CD - hello-service)
3. Cliquez sur le run le plus récent

### Détails disponibles
- ✅ Résumé des tests (succès/échecs)
- ✅ Logs détaillés pour chaque étape
- ✅ Artefacts téléchargés (rapports de test)
- ✅ Rapports de sécurité (CVE, secrets, Trivy)

### Rapports de sécurité
- **Security** → **Code scanning** : CVE, secrets, Trivy
- **Dependabot alerts** : Mise à jour des dépendances

---

## 🐛 Dépannage

### ❌ Pipeline échoue sur les tests
```
1. Vérifiez le log détaillé : Actions → Run → test
2. Exécutez localement : ./gradlew clean test
3. Committer le fix et repousser
```

### ❌ CVE détecté
```
1. Consultez le rapport : Security → Code scanning
2. Identifiez la dépendance vulnérable
3. Mettez à jour build.gradle
4. Lancez ./gradlew clean test localement
5. Committez et poussez
```

### ❌ Image Docker non pushée
```
Vérifier si le push ne se fait que sur main:
push: ${{ github.event_name == 'push' && github.ref == 'refs/heads/main' }}
```

### ❌ Authentification GHCR échoue
```
1. Utilisez GITHUB_TOKEN (déjà configuré) ✅
2. Pas besoin de secret supplémentaire
3. Si erreur persiste : Settings → Actions → Permissions → Read & Write
```

---

## 📈 Prochaines étapes (Phase 3 & 4)

Une fois les pipelines testées, vous pouvez ajouter :

### Phase 3 : Tests d'intégration avec Kind
```yaml
- name: Set up Kind cluster
  uses: helm/kind-action@v1.7.0

- name: Deploy with docker-compose
  run: docker-compose up -d

- name: Run E2E tests
  run: ./scripts/e2e-tests.sh
```

### Phase 4 : Déploiement en staging
```yaml
- name: Deploy to staging
  run: |
    kubectl set image deployment/car-rental-service \
      car-rental=${{ env.REGISTRY }}/${{ env.IMAGE_NAME }}:latest
```

---

## 📚 Ressources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Docker Build Action](https://github.com/docker/build-push-action)
- [Trivy Security Scanner](https://github.com/aquasecurity/trivy-action)
- [Grype CVE Scanner](https://github.com/anchore/grype-action)
- [Gitleaks Secrets Detection](https://github.com/gitleaks/gitleaks-action)

---

## 🎓 Pour les étudiants

### Comprendre la pipeline
```
Code → Tests (JUnit) → Build Gradle → CVE Scan → 
Docker Build → Trivy Scan → Push sur GHCR
```

### Les bonnes pratiques illustrées
✅ Tests automatisés à chaque push  
✅ Détection des vulnérabilités de dépendances  
✅ Détection des secrets accidentels  
✅ Construction reproductible via Docker  
✅ Images optimisées avec multi-stage builds  
✅ Scan de sécurité des images  

### Comment cela aide en production
- Aucun changement ne passe sans tests
- Les vulnérabilités sont détectées avant deployment
- Les images sont construites de manière cohérente
- Historique complet dans GitHub
- Possibilité de rollback rapide
