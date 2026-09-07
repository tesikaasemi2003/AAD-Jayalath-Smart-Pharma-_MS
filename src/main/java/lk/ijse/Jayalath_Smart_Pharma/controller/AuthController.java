package lk.ijse.Jayalath_Smart_Pharma.controller;


import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.AuthResponseDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.GoogleAuthRequestDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.LoginRequestDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public CommonResponse login(@RequestBody LoginRequestDTO loginDTO) {
        AuthResponseDTO response = authService.authenticateUser(loginDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, response);
    }

    @PostMapping("/google")
    public CommonResponse googleLogin(@RequestBody GoogleAuthRequestDTO googleDTO) {
        AuthResponseDTO response = authService.authenticateGoogleUser(googleDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, response);
    }
}
