package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.*;
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
@Table(name = "drug_batches")
public class DrugBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    @Column(unique = true , nullable = false )
    private String batchNumber;
    @Column( nullable = false )
    private LocalDate manufactureDate;

    @Column( nullable = false )
    private LocalDate expiryDate;
    private Double purchasePrice;
    private Double sellingPrice;
    private Double discountPercentage;

    @Column(unique = true , nullable = false )
    private String barcodeString;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id", nullable = false)
    private Drug drug;

    @OneToOne(mappedBy = "drugBatch", cascade= CascadeType.ALL)
    private Inventory inventory;

}
