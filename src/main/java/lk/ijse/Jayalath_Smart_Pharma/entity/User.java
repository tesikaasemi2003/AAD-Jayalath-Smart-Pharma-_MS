package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.*;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.authProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;
    private String fullName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private authProvider authProvider;
    private boolean enabled;
}
