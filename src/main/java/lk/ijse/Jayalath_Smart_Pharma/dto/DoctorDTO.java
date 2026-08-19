package lk.ijse.Jayalath_Smart_Pharma.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private long doctorId;
    private String doctorName;
    private String slmcRegistrationNo;
    private String hospital;
}
