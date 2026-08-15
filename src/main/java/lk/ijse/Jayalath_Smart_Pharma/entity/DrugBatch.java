package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DrugBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String batchId;
    private long drugId;
    private String BatchNumber;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
    private Double purchasePrice;
    private Double sellingPrice;
    private Double discountPercentage;
    private String barcodeString;

}
