package lk.ijse.Jayalath_Smart_Pharma.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrugDTO {
    private long drugId;
    private String brandName;
    private String genericName;
    private int reorderLevel;
    private String unit;
}
