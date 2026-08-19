package lk.ijse.Jayalath_Smart_Pharma.dto;


import lk.ijse.Jayalath_Smart_Pharma.entity.Drug;
import lk.ijse.Jayalath_Smart_Pharma.entity.Prescription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItemDTO {
    private long presItemId;
    private Prescription prescription;
    private Drug drug;
    private String dosage;
    private String frequency;
    private int durationDays;
}
