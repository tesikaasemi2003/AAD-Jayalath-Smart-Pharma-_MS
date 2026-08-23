package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    public void saveRole(RoleDTO roleDTO);
    public List<RoleDTO> getAllRoles();
}
