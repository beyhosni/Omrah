# 🕌 Plateforme Omra

Application web pour optimiser la planification de l'Omra pour les musulmans.

## 📋 Table des matières

- [Présentation](#présentation)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [Démarrage rapide](#démarrage-rapide)
- [Tests](#tests)
- [Documentation](#documentation)

## 🎯 Présentation

La plateforme Omra est une application complète qui aide les musulmans à planifier leur voyage d'Omra en optimisant :
- Les vols depuis leur ville de départ
- Les hôtels à La Mecque et Médine
- La répartition des jours entre les deux villes saintes
- Le budget total du voyage

L'application utilise un algorithme d'optimisation qui prend en compte :
- Le budget disponible
- La distance des hôtels par rapport aux lieux saints
- Les notes et étoiles des hôtels
- Le nombre de personnes

## 🏗️ Architecture

L'application suit une architecture microservices avec 4 services backend et un frontend :

```
omra-platform/
├── auth-service/          # Service d'authentification (JWT)
├── flight-service/        # Service de gestion des vols
├── hotel-service/         # Service de gestion des hôtels
├── planning-service/      # Service d'optimisation des plans
├── omra-frontend/         # Interface utilisateur (React + Vite)
├── docker-compose.yml     # Configuration Docker
└── README-DOCKER.md       # Documentation Docker
```

### Services

#### 🔐 Auth Service (Port 8081)
- Inscription et connexion des utilisateurs
- Génération et validation de tokens JWT
- Gestion sécurisée des mots de passe (BCrypt)

#### ✈️ Flight Service (Port 8082)
- Recherche de vols par ville de départ/arrivée et date
- Gestion des informations de vol (compagnie, prix, durée, escales)

#### 🏨 Hotel Service (Port 8083)
- Recherche d'hôtels par ville
- Informations détaillées (étoiles, distance, prix, notes)
- Gestion des villes et des chambres

#### 📊 Planning Service (Port 8084)
- Calcul du plan optimal basé sur les critères utilisateur
- Algorithme d'optimisation multi-critères
- Agrégation des données des autres services

#### 🎨 Frontend (Port 3000)
- Interface utilisateur moderne et responsive
- Pages de connexion, configuration et résultats
- Communication avec les services backend

## 🛠️ Technologies

### Backend
- **Java 17** - Langage de programmation
- **Spring Boot 3.3.0** - Framework backend
- **Spring Security** - Sécurité et authentification
- **Spring Data JPA** - Accès aux données
- **PostgreSQL** - Base de données
- **OpenFeign** - Communication inter-services
- **JWT** - Authentification stateless
- **JUnit 5 & Mockito** - Tests unitaires et d'intégration

### Frontend
- **React 18** - Bibliothèque UI
- **Vite** - Build tool moderne
- **JavaScript** - Langage de programmation

### DevOps
- **Docker** - Conteneurisation
- **Docker Compose** - Orchestration des conteneurs
- **Maven** - Gestion des dépendances et build

## 🚀 Démarrage rapide

### Prérequis

- Docker Desktop (Windows/Mac) ou Docker Engine + Docker Compose (Linux)
- 4 GB de RAM minimum
- Ports disponibles : 3000, 5432-5434, 8081-8084

### Option 1 : Avec Docker (Recommandé)

#### Windows
```bash
start.bat
```

#### Linux/Mac
```bash
chmod +x start.sh
./start.sh
```

### Option 2 : Démarrage manuel

1. **Démarrer les bases de données PostgreSQL**
```bash
# Créer les bases de données
createdb authdb
createdb flightdb
createdb hoteldb
```

2. **Démarrer les services backend**
```bash
# Auth Service
cd auth-service
mvn spring-boot:run

# Flight Service
cd flight-service
mvn spring-boot:run

# Hotel Service
cd hotel-service
mvn spring-boot:run

# Planning Service
cd planning-service
mvn spring-boot:run
```

3. **Démarrer le frontend**
```bash
cd omra-frontend
npm install
npm run dev
```

### Accès aux services

Une fois démarrés, les services sont accessibles :

- **Frontend** : http://localhost:3000
- **Auth Service** : http://localhost:8081
- **Flight Service** : http://localhost:8082
- **Hotel Service** : http://localhost:8083
- **Planning Service** : http://localhost:8084

## 🧪 Tests

### Exécuter tous les tests

#### Windows
```bash
run-tests.bat
```

#### Linux/Mac
```bash
chmod +x run-tests.sh
./run-tests.sh
```

### Exécuter les tests d'un service spécifique

```bash
cd auth-service
mvn test

# Avec couverture de code
mvn test jacoco:report
```

### Types de tests

- **Tests unitaires** : Tests des services et composants isolés
- **Tests d'intégration** : Tests des contrôleurs avec base de données H2
- **Tests de sécurité** : Tests des endpoints sécurisés

## 📚 Documentation

### Documentation détaillée

- [Guide Docker](README-DOCKER.md) - Instructions complètes pour Docker
- [API Documentation](#) - Documentation des endpoints REST (à venir)

### Structure du projet

```
omra-platform/
├── auth-service/
│   ├── src/
│   │   ├── main/java/com/omra/auth/
│   │   │   ├── config/          # Configuration Spring Security
│   │   │   ├── controller/      # Endpoints REST
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # Entités JPA
│   │   │   ├── exception/       # Gestion des exceptions
│   │   │   ├── repository/      # Repositories JPA
│   │   │   ├── security/        # Services JWT
│   │   │   └── service/         # Logique métier
│   │   └── test/                # Tests unitaires et d'intégration
│   ├── Dockerfile
│   └── pom.xml
├── flight-service/
│   └── [structure similaire]
├── hotel-service/
│   └── [structure similaire]
├── planning-service/
│   └── [structure similaire]
├── omra-frontend/
│   ├── src/
│   │   ├── components/          # Composants réutilisables
│   │   ├── pages/               # Pages de l'application
│   │   ├── services/            # Services API
│   │   └── hooks/               # Hooks React personnalisés
│   ├── Dockerfile
│   ├── nginx.conf
│   └── package.json
├── docker-compose.yml
├── .dockerignore
├── start.sh / start.bat
└── run-tests.sh / run-tests.bat
```

## 🔧 Configuration

### Variables d'environnement

Les services peuvent être configurés via des variables d'environnement :

#### Auth Service
- `SPRING_DATASOURCE_URL` - URL de la base de données
- `JWT_SECRET` - Clé secrète pour JWT (à changer en production)

#### Flight Service
- `SPRING_DATASOURCE_URL` - URL de la base de données

#### Hotel Service
- `SPRING_DATASOURCE_URL` - URL de la base de données

#### Planning Service
- `SERVICES_FLIGHT_URL` - URL du service de vols
- `SERVICES_HOTEL_URL` - URL du service d'hôtels

## 🐛 Dépannage

### Les services ne démarrent pas
1. Vérifier que Docker est en cours d'exécution
2. Vérifier que les ports ne sont pas déjà utilisés
3. Consulter les logs : `docker-compose logs -f`

### Erreurs de connexion à la base de données
1. Attendre que les healthchecks passent au vert
2. Redémarrer les services : `docker-compose restart`

### Problèmes de build
1. Nettoyer le cache Maven : `mvn clean`
2. Reconstruire sans cache : `docker-compose build --no-cache`

## 📝 Améliorations apportées

### Code
- ✅ Ajout de validation des DTOs avec Bean Validation
- ✅ Gestion centralisée des exceptions
- ✅ Ajout de logging avec SLF4J
- ✅ Création des repositories manquants (CityRepository, RoomRepository)
- ✅ Ajout d'un service HotelService
- ✅ Configuration CORS pour le frontend
- ✅ Externalisation des configurations via variables d'environnement

### Tests
- ✅ Tests unitaires pour tous les services
- ✅ Tests d'intégration avec base H2
- ✅ Tests des contrôleurs avec MockMvc
- ✅ Configuration de profils de test

### DevOps
- ✅ Dockerfiles pour tous les services
- ✅ Docker Compose avec healthchecks
- ✅ Scripts de démarrage (Windows et Linux)
- ✅ Scripts d'exécution des tests
- ✅ Configuration nginx pour le frontend
- ✅ Documentation Docker complète

## 📄 Licence

Ce projet est sous licence MIT.

## 👥 Contribution

Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou une pull request.

## 📧 Contact

Pour toute question, veuillez ouvrir une issue sur le repository.
