package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.UserDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.User;
import lk.ijse.Jayalath_Smart_Pharma.repository.UserRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Executing getAllUsers method");
        try{
            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOs = new ArrayList<>();
            for (User user : users) {
                UserDTO userDTO = new UserDTO();
                userDTO.setUserId(user.getUserId());
                userDTO.setFullName(user.getFullName());
                userDTO.setEmail(user.getEmail());
                userDTOs.add(userDTO);
            }
            return userDTOs;
        }catch(Exception e){
            log.error("Error in getAllUsers " + e.getMessage());
            throw e;
        }
    }
    @Override
    public UserDTO getUserById(long userId) {
        log.info("Executing getUserById method");
        try{
            Optional<User> optionalUser = userRepository.findById(userId);
            if(!optionalUser.isPresent()){
                throw new RuntimeException("User not found");
            }
            User  user = optionalUser.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setFullName(user.getFullName());
            userDTO.setEmail(user.getEmail());
            return userDTO;

        }catch(Exception e){
            log.error("Error in getUserById " + e.getMessage());
            throw e;
        }
    }
    @Override
    @Transactional
    public void updateUser( Long userId,UserDTO userDTO) {
        log.info("Executing updateUser method");
        try{
            Optional<User> optionalUser = userRepository.findById(userId);
            if(!optionalUser.isPresent()){
                throw new RuntimeException("User not found");
            }
            User  user = optionalUser.get();
            userDTO.setFullName(user.getFullName());
            userDTO.setEmail(user.getEmail());
            if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()){
                user.setPassword(userDTO.getPassword());
            }
            userRepository.save(user);
        }catch(Exception e){
            log.error("Error in saving User" + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        log.info("Executing deleteUser method");
        try{
            Optional<User> optionalUser = userRepository.findById(userId);
            if(!optionalUser.isPresent()){
                throw new RuntimeException("User not found");
            }
            userRepository.delete(optionalUser.get());
        } catch (Exception e) {
            log.error("Error in deleting User" + e.getMessage());
            throw e;
        }
    }
}
