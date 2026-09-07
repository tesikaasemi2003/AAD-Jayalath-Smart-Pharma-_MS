package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage;
import lk.ijse.Jayalath_Smart_Pharma.dto.AuthResponseDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.GoogleAuthRequestDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.LoginRequestDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Role;
import lk.ijse.Jayalath_Smart_Pharma.entity.User;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.authProvider;
import lk.ijse.Jayalath_Smart_Pharma.repository.UserRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponseDTO authenticateUser(LoginRequestDTO loginDTO) {
        log.info("Attempting authentication for email: {}", loginDTO.getUsername());

        // Username ලෙස එන අගය Email එකක් ලෙස සොයයි
        Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getUsername());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException(ResponseMessage.NOT_FOUND + ": User not found");
        }

        User user = optionalUser.get();

        if (user.getPassword() == null || !user.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String generatedToken = "jwt-token-" + UUID.randomUUID();

        // Set<Role> එකෙන් පළමු Role එක ලබා ගැනීම
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
            // අලුත් Google User කෙනෙකු සාදයි
            user = new User();
            user.setFullName("Google User");
            user.setEmail(mockGoogleEmail);
            user.setAuthProvider(authProvider.GOOGLE); // ඔබගේ authProvider Enum එක අනුව set වේ
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