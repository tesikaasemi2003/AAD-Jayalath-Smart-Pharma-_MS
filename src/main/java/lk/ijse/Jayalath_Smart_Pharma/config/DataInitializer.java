package lk.ijse.Jayalath_Smart_Pharma.config;

import lk.ijse.Jayalath_Smart_Pharma.entity.Role;
import lk.ijse.Jayalath_Smart_Pharma.entity.User;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.RoleName;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.authProvider;
import lk.ijse.Jayalath_Smart_Pharma.repository.RoleRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Seed all roles the app depends on — not just ROLE_ADMIN
        Role adminRole = seedRole(RoleName.ROLE_ADMIN);
        seedRole(RoleName.ROLE_PHARMACIST);
        seedRole(RoleName.ROLE_CASHIER);

        if (userRepository.findByEmail("tesikaasemiagp2003@gmail.com").isEmpty()) {
            User user = new User();
            user.setFullName("Tesika Asemi");
            user.setEmail("tesikaasemiagp2003@gmail.com");
            user.setPassword(passwordEncoder.encode("password123")); // create BCrypt hash automatically
            user.setAuthProvider(authProvider.LOCAL);
            user.setRole(Set.of(adminRole));

            userRepository.save(user);
            System.out.println("====== DEFAULT ADMIN USER CREATED SUCCESSFULY ======");
        }
    }

    private Role seedRole(RoleName roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setRoleName(roleName);
                    return roleRepository.save(role);
                });
    }
}