# 📚 Documentation CI/CD - Index

Bienvenue dans la documentation CI/CD du projet rental-microservices !

## 🚀 Par où commencer ?

### Pour les impatients (5 min)
👉 Lire: **[STUDENT-CI-CD-QUICKSTART.md](STUDENT-CI-CD-QUICKSTART.md)**
- Qu'est-ce qui se passe maintenant ?
- Où voir les résultats ?
- Si quelque chose échoue

### Pour comprendre l'architecture (15 min)
👉 Lire: **[CI-CD-ARCHITECTURE.md](CI-CD-ARCHITECTURE.md)**
- Diagramme visuel de la pipeline
- Détail de chaque phase
- Flux de déclenchement
- Métriques et observabilité

### Pour les détails techniques (20 min)
👉 Lire: **[CI-CD-PIPELINE.md](CI-CD-PIPELINE.md)**
- Configuration complète des workflows
- Étapes détaillées (Phase 1 & 2)
- Comment configurer (optionnel Docker Hub)
- Dépannage avancé

### Avant de faire un push (10 min)
👉 Lire: **[LOCAL-TESTING-CHECKLIST.md](LOCAL-TESTING-CHECKLIST.md)**
- Comment tester localement Phase 1
- Comment tester localement Phase 2
- Checklist complète avant push
- Troubleshooting

---

## 📁 Structure des fichiers

```
.github/
├── workflows/
│   ├── ci-car-rental.yml ........... Car-rental-service pipeline
│   └── ci-hello.yml ............... Hello-service pipeline

docs/
├── README.md (ce fichier)
├── STUDENT-CI-CD-QUICKSTART.md ..... Guide rapide (étudiants)
├── CI-CD-ARCHITECTURE.md ........... Diagrammes et architecture
├── CI-CD-PIPELINE.md .............. Documentation technique
└── LOCAL-TESTING-CHECKLIST.md ..... Validation locale
```

---

## 🎯 Vue d'ensemble rapide

### Phase 1 : Tests & Qualité (1-2 min)
```
Code → JUnit Tests → CVE Scan → Secret Detection → Build Gradle
```

### Phase 2 : Docker (2-5 min)
```
Dockerfile → Docker Build → Push GHCR → Trivy Scan
```

### Total : 5-11 min (jobs en parallèle)

---

## 📊 Déclenchement automatique

| Event | Phase 1 | Phase 2 | Résultat |
|-------|---------|---------|----------|
| Push sur `main` | ✅ | ✅ | Image pushée sur GHCR |
| Push sur `develop` | ✅ | ✅ | Image pushée sur GHCR |
| Pull Request | ✅ | ✅ | Test mode (pas de push) |
| Changement service spécifique | ✅ | ✅ | Seul ce service testé |

---

## 🔐 Sécurité

Aucune configuration requise !

- ✅ GITHUB_TOKEN automatique
- ✅ Authentification GHCR automatique
- ✅ Permissions limitées au repository
- ✅ Token révoqué après le workflow

---

## 📈 Cas d'usage pédagogiques

Cette pipeline enseigne :

✅ Automatisation des tests (CI/CD)  
✅ Détection des vulnérabilités  
✅ Docker containerization  
✅ Git workflows professionnels  
✅ DevOps best practices  
✅ Infrastructure as Code  
✅ Monitoring & observabilité  

---

## 🚀 Prochaines étapes

Une fois maîtrisée, vous pouvez ajouter :

- **Phase 3** : Tests d'intégration avec Kind/docker-compose
- **Phase 4** : Déploiement automatisé en staging/production

---

## 📞 Questions fréquentes

**Q: Pourquoi mes tests passent localement mais échouent en CI ?**  
A: Consultez [STUDENT-CI-CD-QUICKSTART.md](STUDENT-CI-CD-QUICKSTART.md#-si-quelque-chose-échoue)

**Q: Comment tester tout localement avant de pusher ?**  
A: Suivez [LOCAL-TESTING-CHECKLIST.md](LOCAL-TESTING-CHECKLIST.md)

**Q: Où voir les résultats après un push ?**  
A: GitHub → Actions → Cliquez sur le dernier run

**Q: Comment configurer Docker Hub à la place de GHCR ?**  
A: Consultez [CI-CD-PIPELINE.md#étape-3-optionnel--configurer-docker-hub](CI-CD-PIPELINE.md#étape-3-optionnel--configurer-docker-hub)

---

## 🎓 Pour les instructeurs

Cette pipeline permet de enseigner :

1. **Semaine 1** : Tests JUnit et MockMVC
2. **Semaine 2** : Build automatisé avec Gradle
3. **Semaine 3** : Docker containerization
4. **Semaine 4** : CI/CD avec GitHub Actions
5. **Semaine 5** : Sécurité (CVE, secrets)

Chaque semaine : les étudiants pushent du code → pipeline s'exécute → résultats dans GitHub

---

## 📚 Ressources externes

- [GitHub Actions Docs](https://docs.github.com/en/actions)
- [Docker Build Action](https://github.com/docker/build-push-action)
- [Trivy Security Scanner](https://github.com/aquasecurity/trivy-action)
- [Grype CVE Scanner](https://github.com/anchore/grype-action)
- [Spring Boot Testing](https://spring.io/guides/gs/testing-web/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

---

## 💡 Bonnes pratiques

✅ Toujours tester localement avant de pusher  
✅ Lire les logs GitHub Actions en cas d'erreur  
✅ Ne pas commiter de secrets (Gitleaks détecte)  
✅ Garder les tests rapides (< 3 min idéalement)  
✅ Une image Docker par service  
✅ Utiliser des tags sémantiques (latest, main-sha, v1.0.0)  

---

## 🎉 Vous êtes prêts !

La pipeline est déployée et prête. À chaque push :
- Tests exécutés automatiquement
- Vulnérabilités détectées
- Images Docker construites
- Tout tracé dans GitHub

Bonnes pratiques DevOps ✅ Implémentées !
