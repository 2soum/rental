# Guide rapide CI/CD pour les étudiants

## 🎯 Qu'est-ce qui se passe maintenant ?

Quand vous faites un `git push`, GitHub Actions exécute automatiquement :

1. **Tests** ✅ - Vérifie que votre code fonctionne
2. **Build** ✅ - Compile le projet
3. **Sécurité** ✅ - Détecte les vulnérabilités
4. **Docker** 🐳 - Crée une image du conteneur
5. **Push** 📤 - Envoie l'image sur internet

Tout ça sans rien faire ! 🎉

---

## 📊 Où voir les résultats ?

### En 3 clics :
1. Allez sur [GitHub → Actions](https://github.com/charroux/rental/actions)
2. Cliquez sur le run le plus récent
3. Consultez les logs détaillés

Exemple :
```
✅ test - PASSED
✅ code-quality - PASSED
✅ build-and-push - PASSED (image pushée sur GHCR!)
```

---

## 🐛 Si quelque chose échoue

### ❌ Test échoue
```bash
# Reproduisez le problème localement
cd car-rental-service
./gradlew clean test

# Fixez le bug, puis :
git add .
git commit -m "Fix: corriger test échoué"
git push
```

### ❌ CVE détecté
```
GitHub vous le dit automatiquement dans Security → Code scanning
Cherchez la dépendance vulnérable et mettez-la à jour
```

### ❌ Image Docker non pushée
C'est normal si vous pushiez sur `develop` !
L'image n'est pushée que si vous pushiez sur `main`

---

## 📦 Récupérer votre image Docker

Une fois pushée sur GHCR, vous pouvez la récupérer :

```bash
# 1. Se connecter à GHCR
docker login ghcr.io -u USERNAME -p TOKEN

# 2. Télécharger l'image
docker pull ghcr.io/charroux/rental/car-rental-service:latest

# 3. Lancer localement
docker run -p 8080:8080 ghcr.io/charroux/rental/car-rental-service:latest
```

---

## 🚀 Cas d'usage pédagogiques

### Pour les étudiants, cela montre :
✅ L'importance des tests automatisés  
✅ Comment détecter les bugs tôt  
✅ La création reproductible de conteneurs  
✅ L'automatisation en entreprise  
✅ Les bonnes pratiques DevOps  

### Workflow réaliste :
```
1. Écrivez du code
2. git commit et git push
3. GitHub Actions teste automatiquement
4. Si OK → image Docker créée
5. Si erreur → recevez une alerte
6. Fixez et repoussiez
```

---

## 💡 Prochaines étapes

Une fois confortable avec cette pipeline, vous pourriez ajouter :

- Tests E2E (Selenium, Playwright)
- Déploiement automatique en staging
- Kubernetes (Kind, Minikube)
- Monitoring en production

---

## ❓ Questions fréquentes

**Q: Pourquoi mes tests passent localement mais échouent en CI ?**  
A: Différences d'environnement (JDK, OS). GitHub utilise Ubuntu, vous peut-être Mac. Vérifiez les logs.

**Q: Pourquoi l'image Docker est grande ?**  
A: C'est normal la première fois. Les couches Docker sont cachées, les builds ultérieurs sont rapides.

**Q: L'image est-elle vraiment poussée sur GHCR ?**  
A: Oui ! Allez sur GitHub → Packages et vous la verrez.

**Q: Combien ça coûte ?**  
A: Gratuit ! GitHub Actions inclut 2000 minutes/mois gratuites.

---

## 📚 Ressources pour en savoir plus

- [GitHub Actions 101](https://docs.github.com/en/actions/quickstart)
- [Docker Multi-stage Builds](https://docs.docker.com/build/building/multi-stage/)
- [Trivy Security Scanner](https://aquasecurity.github.io/trivy/)
- [CVE - Qu'est-ce que c'est ?](https://www.wikipedia.org/wiki/Common_Vulnerabilities_and_Exposures)
