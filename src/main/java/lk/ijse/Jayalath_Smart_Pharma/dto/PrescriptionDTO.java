package lk.ijse.Jayalath_Smart_Pharma.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lk.ijse.Jayalath_Smart_Pharma.entity.Doctor;
import lk.ijse.Jayalath_Smart_Pharma.entity.Patient;
import lk.ijse.Jayalath_Smart_Pharma.entity.PrescriptionItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {
    private long prescriptionId;
    private long patientId;
    private long doctorId;
    private LocalDate issuedDate;
    private String remarks;
    private List<PrescriptionItemDTO> items ;
}
