package lk.ijse.Jayalath_Smart_Pharma.dto;


import lk.ijse.Jayalath_Smart_Pharma.entity.DrugBatch;
import lk.ijse.Jayalath_Smart_Pharma.entity.SalesOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderItemDTO {
    private long orderItemId;
    private SalesOrder salesOrder;
    private DrugBatch drugBatch;
    private int quantity;
    private double unitPrice;
    private double subTotal;
}
