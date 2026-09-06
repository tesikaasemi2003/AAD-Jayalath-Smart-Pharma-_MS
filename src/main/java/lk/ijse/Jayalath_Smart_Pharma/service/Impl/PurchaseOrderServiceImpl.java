package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.PurchaseOrderDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.PurchaseOrderItemDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Drug;
import lk.ijse.Jayalath_Smart_Pharma.entity.PurchaseOrder;
import lk.ijse.Jayalath_Smart_Pharma.entity.PurchaseOrderItem;
import lk.ijse.Jayalath_Smart_Pharma.repository.DrugRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.PurchaseOrderRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.SupplierRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lk.ijse.Jayalath_Smart_Pharma.entity.Supplier;

@Service
@Slf4j
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final DrugRepository drugRepository;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
                                    SupplierRepository supplierRepository,
                                    DrugRepository drugRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierRepository = supplierRepository;
        this.drugRepository = drugRepository;
    }

    @Override
    @Transactional
    public void createPurchaseOrder(PurchaseOrderDTO dto) {
        log.info("Creating new Purchase Order");
        try {
            PurchaseOrder purchaseOrder = new PurchaseOrder();

            purchaseOrder.setPoNumber(dto.getPoNumber() != null ? dto.getPoNumber() :
                    "PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

            if (dto.getSupplierId() > 0) {
                Optional<Supplier> optionalSupplier = supplierRepository.findById(dto.getSupplierId());
                if (optionalSupplier.isEmpty()) {
                    throw new RuntimeException("Supplier Not Found for ID: " + dto.getSupplierId());
                }
                purchaseOrder.setSupplier(optionalSupplier.get());
            }

            purchaseOrder.setStatus(dto.getStatus());
            purchaseOrder.setCreatedDate(dto.getCreatedDate() != null ? dto.getCreatedDate() : LocalDateTime.now());
            purchaseOrder.setSentDate(dto.getSentDate());

            double totalCalculatedCost = 0.0;
            List<PurchaseOrderItem> orderItems = new ArrayList<>();

            if (dto.getItems() != null && !dto.getItems().isEmpty()) {
                for (PurchaseOrderItemDTO itemDTO : dto.getItems()) {
                    Optional<Drug> optionalDrug = drugRepository.findById(itemDTO.getDrugId());
                    if (optionalDrug.isEmpty()) {
                        throw new RuntimeException("Drug Not Found for ID: " + itemDTO.getDrugId());
                    }

                    PurchaseOrderItem item = new PurchaseOrderItem();
                    item.setPurchaseOrder(purchaseOrder);
                    item.setDrug(optionalDrug.get());
                    item.setQuantityRequested(itemDTO.getQuantityRequested());
                    item.setEstimatedUnitCost(itemDTO.getEstimatedUnitCost());

                    totalCalculatedCost += (itemDTO.getQuantityRequested() * itemDTO.getEstimatedUnitCost());
                    orderItems.add(item);
                }
            } else {
                throw new RuntimeException("Purchase Order must contain at least one item");
            }

            purchaseOrder.setTotalCost(totalCalculatedCost);
            purchaseOrder.setItems(orderItems);

            purchaseOrderRepository.save(purchaseOrder);

        } catch (Exception e) {
            log.error("Error creating purchase order: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<PurchaseOrderDTO> getAllPurchaseOrders() {
        log.info("Fetching all purchase orders");
        List<PurchaseOrder> orders = purchaseOrderRepository.findAll();
        List<PurchaseOrderDTO> dtoList = new ArrayList<>();
        for (PurchaseOrder order : orders) {
            dtoList.add(convertToDTO(order));
        }
        return dtoList;
    }

    @Override
    public PurchaseOrderDTO getPurchaseOrderById(Long poId) {
        log.info("Fetching purchase order for ID: {}", poId);
        Optional<PurchaseOrder> optional = purchaseOrderRepository.findById(poId);
        if (optional.isEmpty()) {
            throw new RuntimeException("Purchase Order Not Found for ID: " + poId);
        }
        return convertToDTO(optional.get());
    }

    private PurchaseOrderDTO convertToDTO(PurchaseOrder order) {
        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.setPoId(order.getPoId());
        dto.setPoNumber(order.getPoNumber());
        dto.setStatus(order.getStatus());
        dto.setCreatedDate(order.getCreatedDate());
        dto.setSentDate(order.getSentDate());
        dto.setTotalCost(order.getTotalCost());

        if (order.getSupplier() != null) {
            dto.setSupplierId(order.getSupplier().getSupplierId());
        }

        List<PurchaseOrderItemDTO> itemDTOs = new ArrayList<>();
        if (order.getItems() != null) {
            for (PurchaseOrderItem item : order.getItems()) {
                PurchaseOrderItemDTO itemDTO = new PurchaseOrderItemDTO();
                itemDTO.setPoItemId(item.getPoItemId());
                itemDTO.setPoId(order.getPoId());
                itemDTO.setQuantityRequested(item.getQuantityRequested());
                itemDTO.setEstimatedUnitCost(item.getEstimatedUnitCost());

                if (item.getDrug() != null) {
                    itemDTO.setDrugId(item.getDrug().getDrugId());
                }
                itemDTOs.add(itemDTO);
            }
        }
        dto.setItems(itemDTOs);
        return dto;
    }
}
