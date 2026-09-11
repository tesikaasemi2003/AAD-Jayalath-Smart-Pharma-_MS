package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage;
import lk.ijse.Jayalath_Smart_Pharma.dto.AuthResponseDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.GoogleAuthRequestDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.LoginRequestDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Role;
import lk.ijse.Jayalath_Smart_Pharma.entity.User;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.RoleName;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.authProvider;
import lk.ijse.Jayalath_Smart_Pharma.repository.RoleRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.UserRepository;
import lk.ijse.Jayalath_Smart_Pharma.security.JwtUtil;
import lk.ijse.Jayalath_Smart_Pharma.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${google.client.id}")
    private String googleClientId;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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

        String generatedToken = jwtUtil.generateToken(user);
        String roleName = extractRoleName(user);

        return new AuthResponseDTO(generatedToken, user.getEmail(), roleName, user.getFullName());
    }

    @Override
    public AuthResponseDTO authenticateGoogleUser(GoogleAuthRequestDTO googleDTO) {
        log.info("Attempting Google Authentication with ID token");

        if (googleDTO.getIdToken() == null || googleDTO.getIdToken().isEmpty()) {
            throw new RuntimeException("Invalid Google Token");
        }

        GoogleIdToken.Payload payload = verifyGoogleIdToken(googleDTO.getIdToken());

        String email = payload.getEmail();
        String fullName = (String) payload.get("name");
        if (fullName == null || fullName.isEmpty()) {
            fullName = email;
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            Role defaultRole = roleRepository.findByRoleName(RoleName.ROLE_CASHIER)
                    .orElseThrow(() -> new RuntimeException("Default role ROLE_CASHIER not found — seed it first"));

            Set<Role> roles = new HashSet<>();
            roles.add(defaultRole);

            user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setAuthProvider(authProvider.GOOGLE);
            user.setActive(true);
            user.setRole(roles);
            user = userRepository.save(user);
        }

        String generatedToken = jwtUtil.generateToken(user);
        String roleName = extractRoleName(user);

        return new AuthResponseDTO(generatedToken, user.getEmail(), roleName, user.getFullName());
    }

    /**
     * Verifies the Google ID token's signature, audience (our Client ID) and expiry
     * server-side. Throws if the token is forged, expired, or issued for a different app.
     */
    private GoogleIdToken.Payload verifyGoogleIdToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new RuntimeException("Invalid or expired Google ID token");
            }
            return idToken.getPayload();
        } catch (Exception e) {
            log.error("Google ID token verification failed: " + e.getMessage());
            throw new RuntimeException("Google ID token verification failed");
        }
    }

    private String extractRoleName(User user) {
        return user.getRole().stream()
                .findFirst()
                .map(role -> role.getRoleName() != null ? role.getRoleName().name() : "USER")
                .orElse("USER");
    }
}