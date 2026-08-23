package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import lk.ijse.Jayalath_Smart_Pharma.dto.RoleDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Role;
import lk.ijse.Jayalath_Smart_Pharma.repository.RoleRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

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
}
