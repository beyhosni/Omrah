#!/bin/bash

echo "=========================================="
echo "  Exécution des tests - Plateforme Omra"
echo "=========================================="
echo ""

# Couleurs pour l'affichage
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Fonction pour exécuter les tests d'un service
run_service_tests() {
    local service=$1
    echo ""
    echo "${YELLOW}📦 Tests pour $service${NC}"
    echo "----------------------------------------"
    
    cd $service
    
    if mvn test; then
        echo "${GREEN}✅ Tests $service: SUCCÈS${NC}"
        cd ..
        return 0
    else
        echo "${RED}❌ Tests $service: ÉCHEC${NC}"
        cd ..
        return 1
    fi
}

# Compteur de résultats
total=0
passed=0
failed=0

# Exécuter les tests pour chaque service
services=("auth-service" "flight-service" "hotel-service" "planning-service")

for service in "${services[@]}"; do
    total=$((total + 1))
    if run_service_tests $service; then
        passed=$((passed + 1))
    else
        failed=$((failed + 1))
    fi
done

# Résumé
echo ""
echo "=========================================="
echo "  📊 Résumé des tests"
echo "=========================================="
echo "Total:   $total services"
echo "${GREEN}Réussis: $passed${NC}"
if [ $failed -gt 0 ]; then
    echo "${RED}Échoués:  $failed${NC}"
fi
echo ""

if [ $failed -eq 0 ]; then
    echo "${GREEN}✅ Tous les tests sont passés avec succès!${NC}"
    exit 0
else
    echo "${RED}❌ Certains tests ont échoué.${NC}"
    exit 1
fi
