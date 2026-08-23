package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.RoleDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Role;
import lk.ijse.Jayalath_Smart_Pharma.repository.RoleRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
 @Override
 @Transactional
    public void saveRole(RoleDTO roleDTO) {
        log.info("Executing saveRole method");
        try {
            Role role = new Role();
            role.setRoleName(roleDTO.getRoleName());
            roleRepository.save(role);
        }catch (Exception e){
            log.error("Error in saveRole method" + e.getMessage());
            throw e;
        }
    }

    public List<RoleDTO> getAllRoles() {
        log.info("Executing getAllRoles method");
        try {
            List<Role> roles = roleRepository.findAll();
            List<RoleDTO> roleDTOs = new ArrayList<>();
            for (Role role : roles) {
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setRoleId(role.getRoleId());
                roleDTO.setRoleName(role.getRoleName());
                roleDTOs.add(roleDTO);
            }
            return roleDTOs;
        }catch(Exception e){
            log.error("Error in getAllRoles method" + e.getMessage());
            throw e;
        }
    }
}
