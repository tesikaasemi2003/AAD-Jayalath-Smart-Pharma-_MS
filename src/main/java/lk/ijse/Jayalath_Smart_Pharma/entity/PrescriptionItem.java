package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "prescription_items")
public class PrescriptionItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
        private long presItemId;

        @ManyToOne
        @JoinColumn(name = "prescription_id")
        private Prescription prescription;

        @ManyToOne
        @JoinColumn(name = "drug_id")
        private Drug drug;

        private String dosage;
        private String frequency;
        private int durationDays;
}
