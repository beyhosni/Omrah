package com.omra.auth.service;

import com.omra.auth.dto.LoginRequest;
import com.omra.auth.dto.LoginResponse;
import com.omra.auth.dto.RegisterRequest;
import com.omra.auth.entity.User;
import com.omra.auth.repository.UserRepository;
import com.omra.auth.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(u -> {
            throw new RuntimeException("Email déjà utilisé");
        });
        User u = new User();
        u.setEmail(request.email());
        u.setFullName(request.fullName());
        u.setPassword(encoder.encode(request.password()));
        userRepository.save(u);
    }

    public LoginResponse login(LoginRequest request) {
        User u = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        if (!encoder.matches(request.password(), u.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        String token = jwtService.generateToken(u.getEmail());
        return new LoginResponse(token);
    }
}
