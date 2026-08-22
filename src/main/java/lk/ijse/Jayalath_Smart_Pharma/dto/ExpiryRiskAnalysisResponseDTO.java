package lk.ijse.Jayalath_Smart_Pharma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpiryRiskAnalysisResponseDTO {
    private Long batchId;
    private String drugName;
    private Integer daysToExpiry;
    private Integer remainingQty;
    private Double recommendedDiscount; // AI Suggested Discount
    private String riskLevel; // HIGH, MEDIUM, LOW
    private String aiReasoning; // Reasoning text
}
