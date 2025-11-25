# Guide de déploiement Docker - Plateforme Omra

## Prérequis

- Docker Desktop installé (Windows/Mac) ou Docker Engine + Docker Compose (Linux)
- Au moins 4 GB de RAM disponible
- Ports disponibles : 3000, 5432-5434, 8081-8084

## Démarrage rapide

### 1. Construire et lancer tous les services

```bash
docker-compose up --build
```

Cette commande va :
- Construire les images Docker pour tous les services
- Créer les bases de données PostgreSQL
- Démarrer tous les microservices
- Démarrer le frontend

### 2. Lancer en arrière-plan

```bash
docker-compose up -d
```

### 3. Vérifier l'état des services

```bash
docker-compose ps
```

### 4. Voir les logs

```bash
# Tous les services
docker-compose logs -f

# Un service spécifique
docker-compose logs -f auth-service
docker-compose logs -f planning-service
```

### 5. Arrêter les services

```bash
# Arrêter sans supprimer les conteneurs
docker-compose stop

# Arrêter et supprimer les conteneurs
docker-compose down

# Arrêter et supprimer les conteneurs + volumes (données)
docker-compose down -v
```

## URLs des services

Une fois démarrés, les services sont accessibles aux URLs suivantes :

- **Frontend** : http://localhost:3000
- **Auth Service** : http://localhost:8081
- **Flight Service** : http://localhost:8082
- **Hotel Service** : http://localhost:8083
- **Planning Service** : http://localhost:8084

## Bases de données

Les bases de données PostgreSQL sont accessibles :

- **Auth DB** : localhost:5432 (authdb)
- **Flight DB** : localhost:5433 (flightdb)
- **Hotel DB** : localhost:5434 (hoteldb)

Credentials par défaut :
- Username: `postgres`
- Password: `postgres`

## Commandes utiles

### Reconstruire un service spécifique

```bash
docker-compose up --build auth-service
```

### Redémarrer un service

```bash
docker-compose restart auth-service
```

### Exécuter des commandes dans un conteneur

```bash
# Accéder au shell d'un conteneur
docker-compose exec auth-service sh

# Exécuter une commande Maven
docker-compose exec auth-service mvn test
```

### Nettoyer complètement

```bash
# Supprimer tous les conteneurs, réseaux et volumes
docker-compose down -v

# Supprimer les images
docker-compose down --rmi all

# Nettoyer le système Docker
docker system prune -a
```

## Développement

### Mode développement avec hot-reload

Pour le développement, vous pouvez monter les volumes locaux :

```yaml
# Ajouter dans docker-compose.yml pour un service
volumes:
  - ./auth-service/src:/app/src
```

### Variables d'environnement

Les variables d'environnement peuvent être modifiées dans `docker-compose.yml` ou via un fichier `.env` :

```env
# .env
JWT_SECRET=your_secret_key
POSTGRES_PASSWORD=your_password
```

## Troubleshooting

### Les services ne démarrent pas

1. Vérifier que les ports ne sont pas déjà utilisés
2. Vérifier les logs : `docker-compose logs`
3. Vérifier l'espace disque disponible

### Erreur de connexion à la base de données

1. Attendre que les healthchecks passent au vert
2. Vérifier les logs de la base de données
3. Redémarrer les services : `docker-compose restart`

### Problèmes de build

1. Nettoyer le cache Docker : `docker builder prune`
2. Reconstruire sans cache : `docker-compose build --no-cache`

### Problèmes de réseau

1. Vérifier que le réseau Docker existe : `docker network ls`
2. Recréer le réseau : `docker-compose down && docker-compose up`

## Production

Pour la production, pensez à :

1. Changer les secrets (JWT_SECRET, mots de passe DB)
2. Utiliser des volumes persistants pour les données
3. Configurer un reverse proxy (nginx, traefik)
4. Activer HTTPS
5. Configurer les limites de ressources
6. Mettre en place la surveillance et les logs centralisés

### Exemple de configuration production

```yaml
auth-service:
  deploy:
    resources:
      limits:
        cpus: '1'
        memory: 512M
      reservations:
        cpus: '0.5'
        memory: 256M
    restart_policy:
      condition: on-failure
      max_attempts: 3
```

## Tests

### Lancer les tests dans Docker

```bash
# Tests unitaires
docker-compose exec auth-service mvn test

# Tests d'intégration
docker-compose exec auth-service mvn verify
```

## Monitoring

Pour monitorer les conteneurs :

```bash
# Statistiques en temps réel
docker stats

# Avec docker-compose
docker-compose top
```
