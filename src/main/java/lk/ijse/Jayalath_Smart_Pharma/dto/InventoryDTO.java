package lk.ijse.Jayalath_Smart_Pharma.dto;

import lk.ijse.Jayalath_Smart_Pharma.entity.DrugBatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private long inventoryId;
    private int quantityOnHand;
    private LocalDateTime lastUpdate;
    private DrugBatch drugBatch;
}
