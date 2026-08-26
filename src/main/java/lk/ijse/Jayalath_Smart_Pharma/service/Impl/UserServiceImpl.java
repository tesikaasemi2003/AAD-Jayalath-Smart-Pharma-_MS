package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.UserDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.User;
import lk.ijse.Jayalath_Smart_Pharma.repository.UserRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void saveUser(UserDTO userDTO) {
        log.info("Executing saveUser method");
        try{
            User user = new User();
            userDTO.setFullName(userDTO.getFullName());
            userDTO.setEmail(userDTO.getEmail());
            userDTO.setPassword(userDTO.getPassword());
            userRepository.save(user);
        }catch(Exception e){
            log.error("Error in saving User" + e.getMessage());
            throw e;
        }
    }
}
