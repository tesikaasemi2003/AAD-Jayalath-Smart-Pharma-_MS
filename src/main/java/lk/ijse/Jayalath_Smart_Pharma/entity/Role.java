package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.*;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false , unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
