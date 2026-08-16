package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.*;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long poId;
    private String poNumber;
    private long supplierId;
    @Enumerated(EnumType.STRING)
    private status status;
    private LocalDateTime createdDate;
    private LocalDateTime sentDate;
    private double totalCost;
}
