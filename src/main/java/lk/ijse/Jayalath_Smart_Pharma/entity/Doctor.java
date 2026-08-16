package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doctorId;
    private String doctorName;

    @Column(unique = true , nullable = false)
    private String slmcRegistrationNo;
    private String hospital;
}
