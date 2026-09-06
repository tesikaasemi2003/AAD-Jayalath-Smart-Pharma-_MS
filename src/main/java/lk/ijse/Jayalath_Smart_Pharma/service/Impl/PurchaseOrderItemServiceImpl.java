package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage;
import lk.ijse.Jayalath_Smart_Pharma.dto.PurchaseOrderItemDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.PurchaseOrderItem;
import lk.ijse.Jayalath_Smart_Pharma.repository.PurchaseOrderItemRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.PurchaseOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PurchaseOrderItemServiceImpl implements PurchaseOrderItemService {
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    public PurchaseOrderItemServiceImpl(PurchaseOrderItemRepository purchaseOrderItemRepository) {
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
    }

    @Override
    public List<PurchaseOrderItemDTO> getAllPurchaseOrderItems() {
        log.info("Fetching all purchase order items");
        List<PurchaseOrderItem> list = purchaseOrderItemRepository.findAll();
        List<PurchaseOrderItemDTO> dtoList = new ArrayList<>();
        for (PurchaseOrderItem item : list) {
            dtoList.add(convertToDTO(item));
        }
        return dtoList;
    }

    @Override
    public PurchaseOrderItemDTO getPurchaseOrderItemById(Long poItemId) {
        log.info("Fetching purchase order item for ID: {}", poItemId);
        Optional<PurchaseOrderItem> optional = purchaseOrderItemRepository.findById(poItemId);
        if (optional.isEmpty()) {
            throw new RuntimeException(ResponseMessage.NOT_FOUND);
        }
        return convertToDTO(optional.get());
    }

    @Override
    public List<PurchaseOrderItemDTO> getItemsByPoId(Long poId) {
        log.info("Fetching purchase order items for PO ID: {}", poId);
        List<PurchaseOrderItem> list = purchaseOrderItemRepository.findByPurchaseOrderPoId(poId);
        List<PurchaseOrderItemDTO> dtoList = new ArrayList<>();
        for (PurchaseOrderItem item : list) {
            dtoList.add(convertToDTO(item));
        }
        return dtoList;
    }

    private PurchaseOrderItemDTO convertToDTO(PurchaseOrderItem item) {
        PurchaseOrderItemDTO dto = new PurchaseOrderItemDTO();
        dto.setPoItemId(item.getPoItemId());
        dto.setQuantityRequested(item.getQuantityRequested());
        dto.setEstimatedUnitCost(item.getEstimatedUnitCost());

        if (item.getPurchaseOrder() != null) {
            dto.setPoId(item.getPurchaseOrder().getPoId());
        }
        if (item.getDrug() != null) {
            dto.setDrugId(item.getDrug().getDrugId());
        }
        return dto;
    }
}
