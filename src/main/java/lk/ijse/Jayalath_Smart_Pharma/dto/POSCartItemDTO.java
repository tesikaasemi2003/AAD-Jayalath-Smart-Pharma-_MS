package lk.ijse.Jayalath_Smart_Pharma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class POSCartItemDTO {
    private Long batchId; // Drug Batch
    private Integer qty;
    private Double unitPrice;
    private Double discountApplied;
}
