package lk.ijse.Jayalath_Smart_Pharma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardMetricsDTO {
    private Double totalDailySales;
    private Integer lowStockDrugCount;
    private Integer expiringBatchesCount;
    private Integer totalPrescriptionsToday;
}
