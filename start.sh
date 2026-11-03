#!/bin/bash

# Script de démarrage rapide pour les microservices
# Usage: ./start.sh [command]

set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
COMPOSE_FILE="$SCRIPT_DIR/docker-compose.yml"

# Couleurs pour l'output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Fonctions
print_header() {
    echo -e "${BLUE}╔════════════════════════════════════════╗${NC}"
    echo -e "${BLUE}║${NC}  $1"
    echo -e "${BLUE}╚════════════════════════════════════════╝${NC}"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_info() {
    echo -e "${YELLOW}ℹ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

# Commandes disponibles
case "${1:-up}" in
    up|start)
        print_header "Démarrage des microservices"
        print_info "Construction des images..."
        docker-compose -f "$COMPOSE_FILE" up --build -d
        
        echo ""
        print_success "Services démarrés !"
        echo ""
        echo -e "${GREEN}Services disponibles :${NC}"
        echo "  Car-Rental Service  : ${BLUE}http://localhost:8080${NC}"
        echo "    • GET  /cars              - Lister les voitures"
        echo "    • GET  /cars/{plate}     - Voiture spécifique"
        echo "    • PUT  /cars/{plate}     - Louer/Rendre"
        echo ""
        echo "  Hello Service       : ${BLUE}http://localhost:8081${NC}"
        echo "    • GET  /hello            - Message Hello"
        echo ""
        echo "Tester les services :"
        echo "  ${YELLOW}curl http://localhost:8080/cars${NC}"
        echo "  ${YELLOW}curl http://localhost:8081/hello${NC}"
        echo ""
        echo "Voir les logs :"
        echo "  ${YELLOW}docker-compose -f $COMPOSE_FILE logs -f${NC}"
        ;;
        
    down|stop)
        print_header "Arrêt des microservices"
        docker-compose -f "$COMPOSE_FILE" down
        print_success "Services arrêtés"
        ;;
        
    logs)
        print_header "Logs des services"
        docker-compose -f "$COMPOSE_FILE" logs -f
        ;;
        
    ps|status)
        print_header "Status des services"
        docker-compose -f "$COMPOSE_FILE" ps
        ;;
        
    build)
        print_header "Compilation des services"
        docker-compose -f "$COMPOSE_FILE" build
        print_success "Build terminé"
        ;;
        
    clean)
        print_header "Nettoyage des conteneurs"
        docker-compose -f "$COMPOSE_FILE" down -v
        print_success "Nettoyage terminé"
        ;;
        
    test)
        print_header "Test des services"
        echo ""
        
        # Test Car-Rental Service
        print_info "Test Car-Rental Service (http://localhost:8080)..."
        if curl -s http://localhost:8080/cars > /dev/null; then
            print_success "Car-Rental Service répond"
            curl -s http://localhost:8080/cars | head -c 200
            echo "..."
        else
            print_error "Car-Rental Service ne répond pas"
        fi
        
        echo ""
        
        # Test Hello Service
        print_info "Test Hello Service (http://localhost:8081)..."
        if curl -s http://localhost:8081/hello > /dev/null; then
            print_success "Hello Service répond"
            curl -s http://localhost:8081/hello
            echo ""
        else
            print_error "Hello Service ne répond pas"
        fi
        ;;
        
    *)
        echo -e "${BLUE}Usage: $0 {up|down|logs|ps|build|clean|test}${NC}"
        echo ""
        echo "Commandes disponibles :"
        echo "  ${GREEN}up${NC}      - Démarrer les services (défaut)"
        echo "  ${GREEN}down${NC}    - Arrêter les services"
        echo "  ${GREEN}logs${NC}    - Afficher les logs"
        echo "  ${GREEN}ps${NC}      - Status des services"
        echo "  ${GREEN}build${NC}   - Compiler les images"
        echo "  ${GREEN}clean${NC}   - Nettoyer les conteneurs"
        echo "  ${GREEN}test${NC}    - Tester les endpoints"
        ;;
esac
