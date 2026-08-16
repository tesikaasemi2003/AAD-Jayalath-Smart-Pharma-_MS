package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrescriptionItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
        private long presItemId;
        private long prescriptionId;
        private long drugId;
        private String dosage;
        private String frequency;
        private int durationDays;
}
