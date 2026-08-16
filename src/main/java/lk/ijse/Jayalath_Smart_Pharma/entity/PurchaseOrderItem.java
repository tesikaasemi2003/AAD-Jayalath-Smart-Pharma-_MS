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
@Table(name = "purchase_order_iems")
public class PurchaseOrderItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long poItemId;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "drug_id")
    private Drug drug;

    private int quantityRequested;
    private double estimatedUnitCost;
}
