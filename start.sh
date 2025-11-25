#!/bin/bash

echo "=========================================="
echo "  Démarrage de la plateforme Omra"
echo "=========================================="
echo ""

# Vérifier si Docker est installé
if ! command -v docker &> /dev/null; then
    echo "❌ Docker n'est pas installé. Veuillez installer Docker Desktop."
    exit 1
fi

# Vérifier si Docker Compose est installé
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose n'est pas installé."
    exit 1
fi

echo "✅ Docker et Docker Compose sont installés"
echo ""

# Arrêter les conteneurs existants
echo "🛑 Arrêt des conteneurs existants..."
docker-compose down

echo ""
echo "🏗️  Construction des images Docker..."
docker-compose build

echo ""
echo "🚀 Démarrage des services..."
docker-compose up -d

echo ""
echo "⏳ Attente du démarrage des services..."
sleep 10

echo ""
echo "📊 État des services:"
docker-compose ps

echo ""
echo "=========================================="
echo "  ✅ Plateforme Omra démarrée!"
echo "=========================================="
echo ""
echo "🌐 URLs des services:"
echo "   - Frontend:         http://localhost:3000"
echo "   - Auth Service:     http://localhost:8081"
echo "   - Flight Service:   http://localhost:8082"
echo "   - Hotel Service:    http://localhost:8083"
echo "   - Planning Service: http://localhost:8084"
echo ""
echo "📝 Commandes utiles:"
echo "   - Voir les logs:    docker-compose logs -f"
echo "   - Arrêter:          docker-compose stop"
echo "   - Redémarrer:       docker-compose restart"
echo "   - Supprimer tout:   docker-compose down -v"
echo ""
echo "Pour plus d'informations, consultez README-DOCKER.md"
echo ""
