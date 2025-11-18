# Omrah
web app to optimise omrah for muslim


omra-platform/
├── pom.xml
├── auth-service/
│   ├── pom.xml
│   └── src/main/java/com/omra/auth/
│       ├── AuthServiceApplication.java
│       ├── controller/AuthController.java
│       ├── dto/LoginRequest.java
│       ├── dto/LoginResponse.java
│       ├── dto/RegisterRequest.java
│       ├── entity/User.java
│       ├── repository/UserRepository.java
│       ├── security/JwtService.java
│       ├── service/AuthService.java
│       └── config/SecurityConfig.java
├── flight-service/
│   ├── pom.xml
│   └── src/main/java/com/omra/flight/
│       ├── FlightServiceApplication.java
│       ├── entity/Flight.java
│       ├── repository/FlightRepository.java
│       ├── service/FlightService.java
│       └── controller/FlightController.java
├── hotel-service/
│   ├── pom.xml
│   └── src/main/java/com/omra/hotel/
│       ├── HotelServiceApplication.java
│       ├── entity/City.java
│       ├── entity/Hotel.java
│       ├── entity/Room.java
│       ├── repository/CityRepository.java
│       ├── repository/HotelRepository.java
│       ├── repository/RoomRepository.java
│       └── controller/HotelController.java
├── planning-service/
│   ├── pom.xml
│   └── src/main/java/com/omra/planning/
│       ├── PlanningServiceApplication.java
│       ├── client/FlightClient.java
│       ├── client/HotelClient.java
│       ├── dto/FlightOption.java
│       ├── dto/HotelOption.java
│       ├── dto/PlanningRequest.java
│       ├── dto/BestPlan.java
│       ├── service/PlanningService.java
│       └── controller/PlanningController.java
└── omra-frontend/
    ├── package.json
    ├── vite.config.js
    └── src/
        ├── main.jsx
        ├── App.jsx
        ├── api.js
        └── pages/
            ├── LoginPage.jsx
            ├── PlanningConfigPage.jsx
            └── PlanningResultPage.jsx
