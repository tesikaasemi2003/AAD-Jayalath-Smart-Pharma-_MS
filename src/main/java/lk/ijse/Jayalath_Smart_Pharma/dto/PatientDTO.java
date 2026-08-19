package lk.ijse.Jayalath_Smart_Pharma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private long patientId;
    private String patientName;
    private String patientPhone;
    private String patientEmail;
    private String nicOrPassport;
}
