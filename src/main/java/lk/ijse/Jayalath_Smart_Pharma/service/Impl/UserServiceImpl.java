package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.UserDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Role;
import lk.ijse.Jayalath_Smart_Pharma.entity.User;
import lk.ijse.Jayalath_Smart_Pharma.repository.RoleRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.UserRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Set<Role> resolveRoles(Set<Role> requestedRoles) {
        Set<Role> resolved = new HashSet<>();
        if (requestedRoles != null) {
            for (Role r : requestedRoles) {
                Role role = roleRepository.findByRoleName(r.getRoleName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + r.getRoleName()));
                resolved.add(role);
            }
        }
        return resolved;
    }

    @Override
    @Transactional
    public void saveUser(UserDTO userDTO) {
        log.info("Executing saveUser method");
        try {
            User user = new User();
            user.setFullName(userDTO.getFullName());
            user.setEmail(userDTO.getEmail());

            boolean isGoogle = userDTO.getAuthProvider() != null
                    && "GOOGLE".equals(userDTO.getAuthProvider().name());

            if (!isGoogle) {
                if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
                    throw new RuntimeException("Password is required for local accounts");
                }
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            user.setAuthProvider(userDTO.getAuthProvider());
            user.setActive(userDTO.isActive());
            user.setRole(resolveRoles(userDTO.getRole()));
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error in saving User" + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Executing getAllUsers method");
        try {
            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOs = new ArrayList<>();
            for (User user : users) {
                userDTOs.add(convertToDTO(user));
            }
            return userDTOs;
        } catch (Exception e) {
            log.error("Error in getAllUsers " + e.getMessage());
            throw e;
        }
    }

    @Override
    public UserDTO getUserById(long userId) {
        log.info("Executing getUserById method");
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (!optionalUser.isPresent()) {
                throw new RuntimeException("User not found");
            }
            return convertToDTO(optionalUser.get());
        } catch (Exception e) {
            log.error("Error in getUserById " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void updateUser(Long userId, UserDTO userDTO) {
        log.info("Executing updateUser method");
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (!optionalUser.isPresent()) {
                throw new RuntimeException("User not found");
            }
            User user = optionalUser.get();
            user.setFullName(userDTO.getFullName());
            user.setEmail(userDTO.getEmail());
            user.setAuthProvider(userDTO.getAuthProvider());
            user.setActive(userDTO.isActive());
            user.setRole(resolveRoles(userDTO.getRole()));

            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error in saving User" + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void updateUserStatus(long userId, boolean active) {
        log.info("Executing updateUserStatus method");
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();
        user.setActive(active);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        log.info("Executing deleteUser method");
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (!optionalUser.isPresent()) {
                throw new RuntimeException("User not found");
            }
            userRepository.delete(optionalUser.get());
        } catch (Exception e) {
            log.error("Error in deleting User" + e.getMessage());
            throw e;
        }
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setAuthProvider(user.getAuthProvider());
        dto.setActive(user.isActive());
        dto.setRole(user.getRole());
        return dto;
    }
}