package lk.ijse.Jayalath_Smart_Pharma.dto;

import jakarta.persistence.*;
import lk.ijse.Jayalath_Smart_Pharma.entity.PurchaseOrderItem;
import lk.ijse.Jayalath_Smart_Pharma.entity.Supplier;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDTO {
    private long poId;
    private String poNumber;
    private Supplier supplier;
    private status status;
    private LocalDateTime createdDate;
    private LocalDateTime sentDate;
    private double totalCost;
    private List<PurchaseOrderItem> items;
}
