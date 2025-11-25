@echo off
echo ==========================================
echo   Demarrage de la plateforme Omra
echo ==========================================
echo.

REM Verifier si Docker est installe
docker --version >nul 2>&1
if errorlevel 1 (
    echo X Docker n'est pas installe. Veuillez installer Docker Desktop.
    pause
    exit /b 1
)

REM Verifier si Docker Compose est installe
docker-compose --version >nul 2>&1
if errorlevel 1 (
    echo X Docker Compose n'est pas installe.
    pause
    exit /b 1
)

echo OK Docker et Docker Compose sont installes
echo.

REM Arreter les conteneurs existants
echo Arret des conteneurs existants...
docker-compose down

echo.
echo Construction des images Docker...
docker-compose build

echo.
echo Demarrage des services...
docker-compose up -d

echo.
echo Attente du demarrage des services...
timeout /t 10 /nobreak >nul

echo.
echo Etat des services:
docker-compose ps

echo.
echo ==========================================
echo   OK Plateforme Omra demarree!
echo ==========================================
echo.
echo URLs des services:
echo    - Frontend:         http://localhost:3000
echo    - Auth Service:     http://localhost:8081
echo    - Flight Service:   http://localhost:8082
echo    - Hotel Service:    http://localhost:8083
echo    - Planning Service: http://localhost:8084
echo.
echo Commandes utiles:
echo    - Voir les logs:    docker-compose logs -f
echo    - Arreter:          docker-compose stop
echo    - Redemarrer:       docker-compose restart
echo    - Supprimer tout:   docker-compose down -v
echo.
echo Pour plus d'informations, consultez README-DOCKER.md
echo.
pause
