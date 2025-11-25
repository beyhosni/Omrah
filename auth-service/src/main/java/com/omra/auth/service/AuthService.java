package com.omra.auth.service;

import com.omra.auth.dto.LoginRequest;
import com.omra.auth.dto.LoginResponse;
import com.omra.auth.dto.RegisterRequest;
import com.omra.auth.entity.User;
import com.omra.auth.exception.InvalidCredentialsException;
import com.omra.auth.exception.UserAlreadyExistsException;
import com.omra.auth.repository.UserRepository;
import com.omra.auth.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) {
        logger.info("Attempting to register user with email: {}", request.getEmail());
        
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new UserAlreadyExistsException("Email déjà utilisé");
        });

        User u = new User();
        u.setEmail(request.getEmail());
        u.setFullName(request.getFullName());
        u.setPassword(encoder.encode(request.getPassword()));
        userRepository.save(u);
        
        logger.info("User registered successfully: {}", request.getEmail());
    }

    public LoginResponse login(LoginRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());
        
        User u = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Email ou mot de passe incorrect"));

        if (!encoder.matches(request.getPassword(), u.getPassword())) {
            throw new InvalidCredentialsException("Email ou mot de passe incorrect");
        }

        String token = jwtService.generateToken(u.getEmail());
        logger.info("User logged in successfully: {}", request.getEmail());
        return new LoginResponse(token);
    }
}
