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
    private double totalSalesToday;
    private long totalOrdersToday;
    private long lowStockCount;
    private long expiringSoonCount;
}
