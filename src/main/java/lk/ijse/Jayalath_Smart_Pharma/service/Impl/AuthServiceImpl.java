package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage;
import lk.ijse.Jayalath_Smart_Pharma.dto.AuthResponseDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.GoogleAuthRequestDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.LoginRequestDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.User;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.authProvider;
import lk.ijse.Jayalath_Smart_Pharma.repository.UserRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponseDTO authenticateUser(LoginRequestDTO loginDTO) {
        log.info("Attempting authentication for email: {}", loginDTO.getUsername());

        Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getUsername());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException(ResponseMessage.NOT_FOUND + ": User not found");
        }

        User user = optionalUser.get();

        if (user.getPassword() == null || !passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String generatedToken = "jwt-token-" + UUID.randomUUID();

        String roleName = user.getRole().stream()
                .findFirst()
                .map(role -> role.getRoleName() != null ? role.getRoleName().name() : "USER")
                .orElse("USER");

        return new AuthResponseDTO(
                generatedToken,
                user.getEmail(),
                roleName,
                user.getFullName()
        );
    }

    @Override
    public AuthResponseDTO authenticateGoogleUser(GoogleAuthRequestDTO googleDTO) {
        log.info("Attempting Google Authentication with ID token");

        if (googleDTO.getIdToken() == null || googleDTO.getIdToken().isEmpty()) {
            throw new RuntimeException("Invalid Google Token");
        }

        String mockGoogleEmail = "googleuser@gmail.com";

        Optional<User> optionalUser = userRepository.findByEmail(mockGoogleEmail);
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = new User();
            user.setFullName("Google User");
            user.setEmail(mockGoogleEmail);
            user.setAuthProvider(authProvider.GOOGLE);
            user = userRepository.save(user);
        }

        String generatedToken = "google-jwt-token-" + UUID.randomUUID();

        String roleName = user.getRole().stream()
                .findFirst()
                .map(role -> role.getRoleName() != null ? role.getRoleName().name() : "USER")
                .orElse("USER");

        return new AuthResponseDTO(
                generatedToken,
                user.getEmail(),
                roleName,
                user.getFullName()
        );
    }
}