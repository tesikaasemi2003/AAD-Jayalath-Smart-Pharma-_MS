package lk.ijse.Jayalath_Smart_Pharma.dto;


import lk.ijse.Jayalath_Smart_Pharma.entity.Drug;
import lk.ijse.Jayalath_Smart_Pharma.entity.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItemDTO {
    private long poItemId;
    private PurchaseOrder purchaseOrder;
    private Drug drug;
    private int quantityRequested;
    private double estimatedUnitCost;
}
