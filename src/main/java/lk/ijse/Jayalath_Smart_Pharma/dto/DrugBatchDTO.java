package lk.ijse.Jayalath_Smart_Pharma.dto;

import jakarta.persistence.*;
import lk.ijse.Jayalath_Smart_Pharma.entity.Drug;
import lk.ijse.Jayalath_Smart_Pharma.entity.Inventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrugBatchDTO {
    private long batchId;
    private String batchNumber;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
    private Double purchasePrice;
    private Double sellingPrice;
    private Double discountPercentage;
    private String barcodeString;
    private Drug drug;
    private Integer quantityOnHand;
    private Long drugId;
}
