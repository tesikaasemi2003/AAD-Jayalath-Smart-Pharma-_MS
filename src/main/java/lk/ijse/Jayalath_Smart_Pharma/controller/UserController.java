package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.UserDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/saveUsers")
    public CommonResponse saveUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @GetMapping("/getAllUsers")
    public CommonResponse getAllUsers() {
        List<UserDTO> userList = userService.getAllUsers();
        return new CommonResponse(OPERATION_SUCCESS , SUCCESS_MESSAGE, userList);
    }

    @GetMapping("/{userId}")
    public CommonResponse getUserById(@PathVariable long userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return new CommonResponse(OPERATION_SUCCESS , SUCCESS_MESSAGE, userDTO);
    }
}
