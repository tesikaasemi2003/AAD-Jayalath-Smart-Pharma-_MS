package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage;
import lk.ijse.Jayalath_Smart_Pharma.dto.SalesOrderItemDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.SalesOrderItem;
import lk.ijse.Jayalath_Smart_Pharma.repository.SalesOrderItemRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.SalesOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SalesOrderItemServiceImpl implements SalesOrderItemService {
    private final SalesOrderItemRepository salesOrderItemRepository;
    public SalesOrderItemServiceImpl(SalesOrderItemRepository salesOrderItemRepository) {
        this.salesOrderItemRepository = salesOrderItemRepository;
    }

    @Override
    public List<SalesOrderItemDTO> getAllSalesOrderItems() {
        log.info("Fetching all sales order items");
        try {
            List<SalesOrderItem> list = salesOrderItemRepository.findAll();
            List<SalesOrderItemDTO> dtoList = new ArrayList<>();
            for (SalesOrderItem item : list) {
                dtoList.add(convertToDTO(item));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error in getAllSalesOrderItems: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public SalesOrderItemDTO getSalesOrderItemById(Long orderItemId) {
        log.info("Fetching sales order item for ID: {}", orderItemId);
        try {
            Optional<SalesOrderItem> optional = salesOrderItemRepository.findById(orderItemId);
            if (optional.isEmpty()) {
                throw new RuntimeException("SalesOrderItem not found for ID: " + orderItemId);
            }
            return convertToDTO(optional.get());
        } catch (Exception e) {
            log.error("Error in getSalesOrderItemById: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<SalesOrderItemDTO> getItemsByOrderId(Long orderId) {
        log.info("Fetching sales order items for Order ID: {}", orderId);
        try {
            List<SalesOrderItem> list = salesOrderItemRepository.findBySalesOrderOrderId(orderId);
            List<SalesOrderItemDTO> dtoList = new ArrayList<>();
            for (SalesOrderItem item : list) {
                dtoList.add(convertToDTO(item));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error in getItemsByOrderId: " + e.getMessage());
            throw e;
        }
    }
    private SalesOrderItemDTO convertToDTO(SalesOrderItem item) {
        SalesOrderItemDTO dto = new SalesOrderItemDTO();
        dto.setOrderItemId(item.getOrderItemId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setSubTotal(item.getSubTotal());

        if (item.getSalesOrder() != null) {
            dto.setOrderId(item.getSalesOrder().getOrderId());
        }
        if (item.getDrugBatch() != null) {
            dto.setBatchId(item.getDrugBatch().getBatchId());
        }
        return dto;
    }
}
