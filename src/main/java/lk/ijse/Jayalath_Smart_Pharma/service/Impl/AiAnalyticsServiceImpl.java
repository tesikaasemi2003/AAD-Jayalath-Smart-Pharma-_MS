package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import lk.ijse.Jayalath_Smart_Pharma.dto.DashboardMetricsDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.ExpiryRiskAnalysisResponseDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.DrugBatch;
import lk.ijse.Jayalath_Smart_Pharma.entity.Inventory;
import lk.ijse.Jayalath_Smart_Pharma.entity.SalesOrder;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.status;
import lk.ijse.Jayalath_Smart_Pharma.repository.DrugBatchRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.InventoryRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.PurchaseOrderRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.SalesOrderRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.AiAnalyticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AiAnalyticsServiceImpl implements AiAnalyticsService {
    private final DrugBatchRepository drugBatchRepository;
    private final InventoryRepository inventoryRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public AiAnalyticsServiceImpl(DrugBatchRepository drugBatchRepository,
                                  InventoryRepository inventoryRepository,
                                  SalesOrderRepository salesOrderRepository,
                                  PurchaseOrderRepository purchaseOrderRepository) {
        this.drugBatchRepository = drugBatchRepository;
        this.inventoryRepository = inventoryRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public List<ExpiryRiskAnalysisResponseDTO> analyzeExpiryRisk() {
        log.info("Running AI Expiry Risk Analysis");
        List<DrugBatch> batches = drugBatchRepository.findAll();
        List<ExpiryRiskAnalysisResponseDTO> riskList = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (DrugBatch batch : batches) {
            if (batch.getExpiryDate() != null) {
                long daysToExpiry = ChronoUnit.DAYS.between(today, batch.getExpiryDate());
                Inventory inventory = batch.getInventory();
                int qty = inventory != null ? inventory.getQuantityOnHand() : 0;

                if (daysToExpiry <= 90 && qty > 0) {
                    ExpiryRiskAnalysisResponseDTO dto = new ExpiryRiskAnalysisResponseDTO();
                    dto.setBatchId(batch.getBatchId());
                    dto.setBatchNumber(batch.getBatchNumber());
                    dto.setExpiryDate(batch.getExpiryDate());
                    dto.setDaysToExpiry(daysToExpiry);
                    dto.setQuantityOnHand(qty);

                    if (batch.getDrug() != null) {
                        dto.setDrugName(batch.getDrug().getBrandName() + " (" + batch.getDrug().getGenericName() + ")");
                    }

                    double currentPrice = batch.getSellingPrice() != null ? batch.getSellingPrice() : 0.0;

                    if (daysToExpiry <= 30) {
                        dto.setRiskLevel("HIGH");
                        dto.setRecommendedAction("Apply 30% discount immediately to clear stock before expiration.");
                        dto.setSuggestedDiscountPrice(currentPrice * 0.70);
                    } else if (daysToExpiry <= 60) {
                        dto.setRiskLevel("MEDIUM");
                        dto.setRecommendedAction("Apply 15% discount and prioritize in POS suggestions.");
                        dto.setSuggestedDiscountPrice(currentPrice * 0.85);
                    } else {
                        dto.setRiskLevel("LOW");
                        dto.setRecommendedAction("Monitor sales velocity.");
                        dto.setSuggestedDiscountPrice(currentPrice);
                    }

                    riskList.add(dto);
                }
            }
        }

        riskList.sort((a, b) -> Long.compare(a.getDaysToExpiry(), b.getDaysToExpiry()));
        return riskList;
    }

    @Override
    public DashboardMetricsDTO getDashboardMetrics() {
        log.info("Fetching Dashboard Metrics");
        DashboardMetricsDTO metrics = new DashboardMetricsDTO();

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);

        List<SalesOrder> todayOrders = salesOrderRepository.findAll().stream()
                .filter(o -> o.getOrderDate() != null &&
                        !o.getOrderDate().isBefore(startOfDay) &&
                        !o.getOrderDate().isAfter(endOfDay))
                .toList();

        double todaySalesSum = todayOrders.stream()
                .mapToDouble(SalesOrder::getNetAmount)
                .sum();

        metrics.setTotalSalesToday(todaySalesSum);
        metrics.setTotalOrdersToday(todayOrders.size());

        List<Inventory> allInventory = inventoryRepository.findAll();
        long lowStock = allInventory.stream()
                .filter(inv -> inv.getQuantityOnHand() < 10)
                .count();
        metrics.setLowStockCount(lowStock);

        // Aligned with the "FEFO Risk · 60 Days" card
        LocalDate riskWindow = LocalDate.now().plusDays(60);
        long expiringSoon = drugBatchRepository.findAll().stream()
                .filter(b -> b.getExpiryDate() != null && !b.getExpiryDate().isAfter(riskWindow))
                .count();
        metrics.setExpiringSoonCount(expiringSoon);

        metrics.setTotalBatches(drugBatchRepository.count());

        long pendingPOs = purchaseOrderRepository.findAll().stream()
                .filter(po -> po.getStatus() == status.DRAFT)
                .count();
        metrics.setPendingPurchaseOrders(pendingPOs);

        return metrics;
    }
}