package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.DashboardMetricsDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.ExpiryRiskAnalysisResponseDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.AiAnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/analytics")
public class AiAnalytictsController {
    private AiAnalyticsService aiAnalyticsService;

    public AiAnalytictsController(AiAnalyticsService aiAnalyticsService) {
        this.aiAnalyticsService = aiAnalyticsService;
    }

    @GetMapping("/expiry-risk")
    public CommonResponse getExpiryRiskAnalysis() {
        List<ExpiryRiskAnalysisResponseDTO> result = aiAnalyticsService.analyzeExpiryRisk();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, result);
    }

    @GetMapping("/dashboard-metrics")
    public CommonResponse getDashboardMetrics() {
        DashboardMetricsDTO metrics = aiAnalyticsService.getDashboardMetrics();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, metrics);
    }
}
