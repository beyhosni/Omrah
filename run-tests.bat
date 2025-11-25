@echo off
setlocal enabledelayedexpansion

echo ==========================================
echo   Execution des tests - Plateforme Omra
echo ==========================================
echo.

set total=0
set passed=0
set failed=0

REM Fonction pour executer les tests d'un service
call :run_service_tests auth-service
call :run_service_tests flight-service
call :run_service_tests hotel-service
call :run_service_tests planning-service

REM Resume
echo.
echo ==========================================
echo   Resume des tests
echo ==========================================
echo Total:   %total% services
echo Reussis: %passed%
echo Echoues: %failed%
echo.

if %failed% equ 0 (
    echo OK Tous les tests sont passes avec succes!
    exit /b 0
) else (
    echo X Certains tests ont echoue.
    exit /b 1
)

:run_service_tests
set /a total+=1
set service=%1
echo.
echo Tests pour %service%
echo ----------------------------------------

cd %service%
call mvn test

if errorlevel 1 (
    echo X Tests %service%: ECHEC
    set /a failed+=1
    cd ..
    goto :eof
) else (
    echo OK Tests %service%: SUCCES
    set /a passed+=1
    cd ..
    goto :eof
)
