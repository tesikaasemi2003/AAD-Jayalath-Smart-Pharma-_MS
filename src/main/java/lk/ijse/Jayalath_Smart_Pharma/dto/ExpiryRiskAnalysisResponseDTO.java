package lk.ijse.Jayalath_Smart_Pharma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpiryRiskAnalysisResponseDTO {
    private Long batchId;
    private String drugName;
    private String batchNumber;
    private LocalDate expiryDate;
    private Long daysToExpiry;
    private Integer quantityOnHand;
    private String riskLevel; // HIGH, MEDIUM, LOW
    private String recommendedAction;
    private double suggestedDiscountPrice;
}
