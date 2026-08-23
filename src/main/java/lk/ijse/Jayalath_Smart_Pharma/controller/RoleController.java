package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.RoleDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public CommonResponse saveRole(@RequestBody RoleDTO roleDTO) {
        roleService.saveRole(roleDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @GetMapping
    public CommonResponse getAllRoles() {
        List<RoleDTO> roleList = roleService.getAllRoles();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, roleList);
    }
}
