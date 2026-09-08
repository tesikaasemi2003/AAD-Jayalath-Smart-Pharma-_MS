package lk.ijse.Jayalath_Smart_Pharma.security;

import lk.ijse.Jayalath_Smart_Pharma.entity.Role;
import lk.ijse.Jayalath_Smart_Pharma.entity.User;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.RoleName;
import lk.ijse.Jayalath_Smart_Pharma.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_success() {
        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ROLE_ADMIN);

        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword("password123");
        user.setRole(Set.of(adminRole));

        when(userRepository.findByEmail("admin@gmail.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("admin@gmail.com");

        assertNotNull(userDetails);
        assertEquals("admin@gmail.com", userDetails.getUsername());

        Set<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertTrue(authorities.contains("ROLE_ADMIN"));
    }

    @Test
    void loadUserByUsername_userNotFound_throwsException() {
        when(userRepository.findByEmail("notfound@gmail.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                customUserDetailsService.loadUserByUsername("notfound@gmail.com")
        );
    }
}