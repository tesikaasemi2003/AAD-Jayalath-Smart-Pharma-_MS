package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.UserDTO;

import java.util.List;

public interface UserService {
    public void saveUser(UserDTO userDTO);
    public List<UserDTO> getAllUsers();
    public UserDTO getUserById(long userId);
}
