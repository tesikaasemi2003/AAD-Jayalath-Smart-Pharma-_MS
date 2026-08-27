package lk.ijse.Jayalath_Smart_Pharma.dto;


import lk.ijse.Jayalath_Smart_Pharma.entity.Category;
import lk.ijse.Jayalath_Smart_Pharma.entity.DrugBatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private Category category;
    private List<DrugBatch> batches;
    private Long categoryId;
}
