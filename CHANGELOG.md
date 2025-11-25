# Changelog

Toutes les modifications notables de ce projet seront documentées dans ce fichier.

## [1.0.0] - 2024-01-XX

### ✨ Ajouté

#### Code
- Validation des DTOs avec Bean Validation (@NotBlank, @Email, @Size)
- Gestion centralisée des exceptions avec @RestControllerAdvice
- Exceptions personnalisées (UserAlreadyExistsException, InvalidCredentialsException)
- Logging avec SLF4J dans tous les services
- Service HotelService pour la logique métier
- Repositories manquants : CityRepository et RoomRepository
- Configuration CORS pour tous les contrôleurs
- Externalisation des configurations via variables d'environnement

#### Tests
- Tests unitaires pour AuthService
- Tests unitaires pour FlightService
- Tests unitaires pour HotelService
- Tests unitaires pour PlanningService
- Tests d'intégration pour AuthController
- Tests d'intégration pour FlightController
- Tests d'intégration pour HotelController
- Configuration de profils de test avec base H2
- Fichiers application-test.yml pour chaque service

#### DevOps
- Dockerfile pour auth-service
- Dockerfile pour flight-service
- Dockerfile pour hotel-service
- Dockerfile pour planning-service
- Dockerfile pour omra-frontend
- docker-compose.yml avec 3 bases PostgreSQL
- Healthchecks pour les bases de données
- Configuration nginx pour le frontend
- Scripts de démarrage (start.sh et start.bat)
- Scripts d'exécution des tests (run-tests.sh et run-tests.bat)
- .dockerignore pour optimiser les builds
- .env.example pour la configuration

#### Documentation
- README.md complet avec instructions détaillées
- README-DOCKER.md avec guide Docker complet
- CHANGELOG.md pour suivre les modifications
- Commentaires dans le code
- Documentation des endpoints REST

### 🔧 Modifié

#### Configuration
- application.yml de auth-service : variables d'environnement
- application.yml de flight-service : variables d'environnement
- application.yml de hotel-service : variables d'environnement
- application.yml de planning-service : variables d'environnement

#### Code
- AuthController : ajout de @Valid et @CrossOrigin
- FlightController : ajout de @CrossOrigin
- HotelController : utilisation de HotelService au lieu du repository direct
- PlanningController : ajout de @CrossOrigin
- AuthService : utilisation d'exceptions personnalisées et logging
- PlanningService : ajout de logging détaillé

#### Dépendances
- Ajout de spring-boot-starter-validation dans auth-service
- Ajout de spring-boot-starter-test dans tous les services
- Ajout de h2 database pour les tests
- Ajout de spring-security-test dans auth-service
- Ajout de spring-cloud-contract-wiremock dans planning-service

### 🐛 Corrigé

- Gestion des erreurs avec messages appropriés
- Validation des données d'entrée
- Sécurité : externalisation du secret JWT
- Problèmes de CORS entre frontend et backend
- Repositories manquants pour City et Room

### 🔒 Sécurité

- Externalisation du secret JWT via variable d'environnement
- Validation stricte des entrées utilisateur
- Hashage des mots de passe avec BCrypt
- Messages d'erreur génériques pour éviter la fuite d'informations
- Configuration CORS appropriée

### 📊 Performance

- Utilisation de multi-stage builds dans les Dockerfiles
- Images Alpine pour réduire la taille
- Healthchecks pour assurer la disponibilité des services
- Optimisation des requêtes JPA

### 🎯 Qualité du code

- Ajout de tests avec couverture significative
- Séparation des responsabilités (Service/Controller/Repository)
- Utilisation de DTOs pour la communication
- Logging approprié pour le debugging
- Gestion d'erreurs robuste

## [0.1.0] - Version initiale

### Ajouté
- Structure de base du projet multi-modules Maven
- Auth Service avec JWT
- Flight Service avec recherche de vols
- Hotel Service avec recherche d'hôtels
- Planning Service avec algorithme d'optimisation
- Frontend React avec Vite
- Configuration Spring Boot de base
- Entités JPA et repositories
