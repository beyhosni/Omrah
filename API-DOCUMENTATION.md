# 📡 Documentation API - Plateforme Omra

## Table des matières

- [Auth Service](#auth-service)
- [Flight Service](#flight-service)
- [Hotel Service](#hotel-service)
- [Planning Service](#planning-service)

---

## 🔐 Auth Service

**Base URL**: `http://localhost:8081`

### POST /api/auth/register

Inscription d'un nouvel utilisateur.

**Request Body**:
```json
{
  "email": "user@example.com",
  "password": "password123",
  "fullName": "John Doe"
}
```

**Validation**:
- `email`: Obligatoire, format email valide
- `password`: Obligatoire, minimum 6 caractères
- `fullName`: Obligatoire

**Response**: `201 Created`
```json
{}
```

**Erreurs**:
- `409 Conflict`: Email déjà utilisé
- `400 Bad Request`: Validation échouée

**Exemple cURL**:
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123",
    "fullName": "John Doe"
  }'
```

---

### POST /api/auth/login

Connexion d'un utilisateur existant.

**Request Body**:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Validation**:
- `email`: Obligatoire, format email valide
- `password`: Obligatoire

**Response**: `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Erreurs**:
- `401 Unauthorized`: Email ou mot de passe incorrect
- `400 Bad Request`: Validation échouée

**Exemple cURL**:
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

---

## ✈️ Flight Service

**Base URL**: `http://localhost:8082`

### GET /api/flights/search

Recherche de vols selon les critères.

**Query Parameters**:
- `from` (string, required): Ville de départ
- `to` (string, required): Ville d'arrivée
- `date` (date, required): Date de départ (format: YYYY-MM-DD)

**Response**: `200 OK`
```json
[
  {
    "id": 1,
    "airline": "Air France",
    "fromCity": "Paris",
    "toCity": "Jeddah",
    "departureTime": "2024-01-01T10:00:00",
    "arrivalTime": "2024-01-01T16:00:00",
    "price": 500.00,
    "durationMinutes": 360,
    "stopsCount": 0
  }
]
```

**Erreurs**:
- `400 Bad Request`: Paramètres invalides

**Exemple cURL**:
```bash
curl -X GET "http://localhost:8082/api/flights/search?from=Paris&to=Jeddah&date=2024-01-01"
```

**Exemple JavaScript**:
```javascript
const response = await fetch(
  'http://localhost:8082/api/flights/search?from=Paris&to=Jeddah&date=2024-01-01'
);
const flights = await response.json();
```

---

## 🏨 Hotel Service

**Base URL**: `http://localhost:8083`

### GET /api/hotels/search

Recherche d'hôtels par ville.

**Query Parameters**:
- `city` (string, optional): Nom de la ville

**Response**: `200 OK`
```json
[
  {
    "id": 1,
    "name": "Grand Hotel Mekka",
    "city": {
      "id": 1,
      "name": "Mekke",
      "country": "Saudi Arabia"
    },
    "stars": 5,
    "distanceMeters": 500,
    "rating": 4.5,
    "address": "123 Main Street"
  }
]
```

**Notes**:
- Si `city` n'est pas fourni, retourne tous les hôtels
- La recherche est insensible à la casse

**Exemple cURL**:
```bash
curl -X GET "http://localhost:8083/api/hotels/search?city=Mekke"
```

**Exemple JavaScript**:
```javascript
const response = await fetch(
  'http://localhost:8083/api/hotels/search?city=Mekke'
);
const hotels = await response.json();
```

---

## 📊 Planning Service

**Base URL**: `http://localhost:8084`

### POST /api/planning/optimal

Calcule le plan optimal pour un voyage Omra.

**Request Body**:
```json
{
  "departureCity": "Paris",
  "dateFrom": "2024-01-01",
  "dateTo": "2024-01-15",
  "persons": 2,
  "budget": 5000.00
}
```

**Validation**:
- `departureCity`: Obligatoire
- `dateFrom`: Obligatoire, format date
- `dateTo`: Obligatoire, format date, doit être après dateFrom
- `persons`: Obligatoire, > 0
- `budget`: Obligatoire, > 0

**Response**: `200 OK`
```json
{
  "flight": {
    "id": 1,
    "airline": "Air France",
    "fromCity": "Paris",
    "toCity": "Jeddah",
    "departureTime": "2024-01-01T10:00:00",
    "arrivalTime": "2024-01-01T16:00:00",
    "price": 500.00,
    "durationMinutes": 360,
    "stopsCount": 0
  },
  "mekkeHotel": {
    "id": 1,
    "name": "Grand Hotel Mekka",
    "city": "Mekke",
    "stars": 5,
    "distanceMeters": 500,
    "rating": 4.5,
    "pricePerNight": 100.00
  },
  "medineHotel": {
    "id": 2,
    "name": "Medina Palace",
    "city": "Medine",
    "stars": 4,
    "distanceMeters": 800,
    "rating": 4.0,
    "pricePerNight": 80.00
  },
  "mekkeDays": 9,
  "medineDays": 5,
  "totalCost": 4200.00,
  "score": 0.85
}
```

**Algorithme d'optimisation**:

Le score est calculé selon la formule :
```
score = 0.5 × (1 - coût/budget) + 0.3 × score_distance + 0.2 × (note_moyenne/5)
```

Où :
- **Coût** (50%) : Favorise les plans moins chers
- **Distance** (30%) : Favorise les hôtels proches des lieux saints
- **Note** (20%) : Favorise les hôtels bien notés

**Répartition des jours**:
- Minimum 3 jours à La Mecque
- Minimum 2 jours à Médine
- Par défaut : 60% du temps à La Mecque, 40% à Médine

**Erreurs**:
- `400 Bad Request`: Dates invalides ou paramètres manquants
- `500 Internal Server Error`: Aucune combinaison ne respecte le budget

**Exemple cURL**:
```bash
curl -X POST http://localhost:8084/api/planning/optimal \
  -H "Content-Type: application/json" \
  -d '{
    "departureCity": "Paris",
    "dateFrom": "2024-01-01",
    "dateTo": "2024-01-15",
    "persons": 2,
    "budget": 5000.00
  }'
```

**Exemple JavaScript**:
```javascript
const response = await fetch('http://localhost:8084/api/planning/optimal', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    departureCity: 'Paris',
    dateFrom: '2024-01-01',
    dateTo: '2024-01-15',
    persons: 2,
    budget: 5000.00
  })
});
const plan = await response.json();
```

---

## 🔒 Authentification

### Utilisation du JWT

Pour les endpoints protégés (à venir), incluez le token JWT dans le header :

```
Authorization: Bearer <token>
```

**Exemple**:
```bash
curl -X GET http://localhost:8081/api/protected \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**Durée de validité**: 24 heures

---

## 📝 Codes de statut HTTP

| Code | Signification | Description |
|------|---------------|-------------|
| 200 | OK | Requête réussie |
| 201 | Created | Ressource créée avec succès |
| 400 | Bad Request | Paramètres invalides ou validation échouée |
| 401 | Unauthorized | Authentification requise ou échouée |
| 404 | Not Found | Ressource non trouvée |
| 409 | Conflict | Conflit (ex: email déjà utilisé) |
| 500 | Internal Server Error | Erreur serveur |

---

## 🧪 Tests avec Postman

### Collection Postman

Importez cette collection pour tester facilement tous les endpoints :

```json
{
  "info": {
    "name": "Omra Platform API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Auth",
      "item": [
        {
          "name": "Register",
          "request": {
            "method": "POST",
            "header": [],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"email\": \"test@example.com\",\n  \"password\": \"password123\",\n  \"fullName\": \"Test User\"\n}",
              "options": {
                "raw": {
                  "language": "json"
                }
              }
            },
            "url": {
              "raw": "http://localhost:8081/api/auth/register",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8081",
              "path": ["api", "auth", "register"]
            }
          }
        },
        {
          "name": "Login",
          "request": {
            "method": "POST",
            "header": [],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"email\": \"test@example.com\",\n  \"password\": \"password123\"\n}",
              "options": {
                "raw": {
                  "language": "json"
                }
              }
            },
            "url": {
              "raw": "http://localhost:8081/api/auth/login",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8081",
              "path": ["api", "auth", "login"]
            }
          }
        }
      ]
    }
  ]
}
```

---

## 🌐 CORS

Tous les endpoints acceptent les requêtes cross-origin avec :
- Origins: `*` (à restreindre en production)
- Methods: `GET, POST, PUT, DELETE, OPTIONS`
- Headers: `Content-Type, Authorization`

---

## 📊 Formats de données

### Dates
Format ISO 8601 : `YYYY-MM-DD` ou `YYYY-MM-DDTHH:mm:ss`

### Nombres décimaux
Format : `1234.56` (point comme séparateur décimal)

### Devises
Toutes les valeurs monétaires sont en USD par défaut.

---

## 🔄 Versioning

Version actuelle : **v1**

Les futures versions seront préfixées : `/api/v2/...`

---

## 📞 Support

Pour toute question sur l'API, ouvrez une issue sur le repository GitHub.
