package lk.ijse.Jayalath_Smart_Pharma.dto;

import jakarta.persistence.*;
import lk.ijse.Jayalath_Smart_Pharma.entity.Role;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.authProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long userId;
    private String fullName;
    private String email;
    private String password;
    private authProvider authProvider;
    private Set<Role> role = new HashSet<>();
}
