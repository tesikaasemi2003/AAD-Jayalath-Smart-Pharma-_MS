package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.DashboardMetricsDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.ExpiryRiskAnalysisResponseDTO;

import java.util.List;

public interface AiAnalyticsService {
    public List<ExpiryRiskAnalysisResponseDTO> analyzeExpiryRisk();
    public DashboardMetricsDTO getDashboardMetrics();
}
