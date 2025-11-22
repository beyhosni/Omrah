# 🕋 Omrah Platform  
Web app to optimise Omrah experience for Muslims — planning, booking & guidance.

---

## ✅ Badges

![Java](https://img.shields.io/badge/Java-17-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-green)
![Microservices](https://img.shields.io/badge/Architecture-Microservices-blue)
![Node.js](https://img.shields.io/badge/Node.js-18-green)
![Vite](https://img.shields.io/badge/Vite-Frontend-purple)
![Docker](https://img.shields.io/badge/Container-Docker-blue)
![CI/CD](https://img.shields.io/badge/GitHub-Actions-lightgrey)
![License](https://img.shields.io/badge/License-MIT-yellow)

---

## 🎯 Project Goal

- Simplify Omrah journey planning  
- Find the **best optimized plan** (flights + hotels)  
- Provide a modern, user-friendly digital platform for pilgrims  
- Centralize travel, accommodation & scheduling needs  

---

## 🧱 System Architecture

flowchart LR
    User[[Frontend Web]] -->|HTTP| Gateway((API Gateway))
    Gateway --> AuthService((Auth Service))
    Gateway --> FlightService((Flight Service))
    Gateway --> HotelService((Hotel Service))
    Gateway --> PlanningService((Planning Service))
    AuthService --> DB1[(Users DB)]
    FlightService --> DB2[(Flights DB)]
    HotelService --> DB3[(Hotels DB)]
    PlanningService --> FlightService
    PlanningService --> HotelService
            └── PlanningResultPage.jsx
