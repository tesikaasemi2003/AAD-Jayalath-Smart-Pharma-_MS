package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.AuthResponseDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.GoogleAuthRequestDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.LoginRequestDTO;

public interface AuthService {
    public AuthResponseDTO authenticateUser(LoginRequestDTO loginDTO);
    public AuthResponseDTO authenticateGoogleUser(GoogleAuthRequestDTO googleDTO);
}
