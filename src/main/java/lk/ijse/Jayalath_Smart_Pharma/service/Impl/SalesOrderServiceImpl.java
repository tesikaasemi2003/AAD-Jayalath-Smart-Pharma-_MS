package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.SalesOrderDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.SalesOrderItemDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.*;
import lk.ijse.Jayalath_Smart_Pharma.repository.DrugBatchRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.PatientRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.SalesOrderRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.UserRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.SalesOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class SalesOrderServiceImpl implements SalesOrderService {
    private final SalesOrderRepository salesOrderRepository;
    private final DrugBatchRepository drugBatchRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository,
                                 DrugBatchRepository drugBatchRepository,
                                 PatientRepository patientRepository,
                                 UserRepository userRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.drugBatchRepository = drugBatchRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void processSalesOrder(SalesOrderDTO dto) {
        log.info("Processing POS Sales Order for Batch ID deductions");
        try {
            SalesOrder order = new SalesOrder();

            // Order Number auto-generate කිරීම (DTO එකේ නැතොත්)
            order.setOrderNumber(dto.getOrderNumber() != null ? dto.getOrderNumber() :
                    "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

            if (dto.getPatientId() > 0) {
                Optional<Patient> optionalPatient = patientRepository.findById(dto.getPatientId());
                optionalPatient.ifPresent(order::setPatient);
            }

            if (dto.getCashierId() > 0) {
                Optional<User> optionalCashier = userRepository.findById(dto.getCashierId());
                optionalCashier.ifPresent(order::setCashier);
            }

            order.setOrderDate(dto.getOrderDate() != null ? dto.getOrderDate() : LocalDateTime.now());
            order.setPaymentType(dto.getPaymentType());
            order.setDiscountAmount(dto.getDiscountAmount());

            double calculatedTotal = 0.0;
            List<SalesOrderItem> orderItems = new ArrayList<>();

            if (dto.getSalesOrderItems() != null && !dto.getSalesOrderItems().isEmpty()) {
                for (SalesOrderItemDTO itemDTO : dto.getSalesOrderItems()) {
                    Optional<DrugBatch> optionalBatch = drugBatchRepository.findById(itemDTO.getBatchId());
                    if (optionalBatch.isEmpty()) {
                        throw new RuntimeException("Drug Batch Not Found for ID: " + itemDTO.getBatchId());
                    }

                    DrugBatch batch = optionalBatch.get();
                    Inventory inventory = batch.getInventory();

                    if (inventory == null) {
                        throw new RuntimeException("Inventory record not found for Batch ID: " + batch.getBatchId());
                    }

// 1. Inventory Stock (quantityOnHand) පරීක්ෂා කිරීම
                    if (inventory.getQuantityOnHand() < itemDTO.getQuantity()) {
                        throw new RuntimeException("Insufficient stock in Batch ID: " + batch.getBatchId() +
                                " (Available: " + inventory.getQuantityOnHand() + ")");
                    }

// 2. Stock එක (quantityOnHand) අඩු කිරීම සහ lastUpdate වේලාව update කිරීම
                    inventory.setQuantityOnHand(inventory.getQuantityOnHand() - itemDTO.getQuantity());
                    inventory.setLastUpdate(LocalDateTime.now());

// 3. Selling Price සහ SubTotal ගණනය කිරීම
                    double unitPrice = itemDTO.getUnitPrice() > 0 ? itemDTO.getUnitPrice() : batch.getSellingPrice();
                    double subTotal = unitPrice * itemDTO.getQuantity();
                    calculatedTotal += subTotal;

                    SalesOrderItem item = new SalesOrderItem();
                    item.setSalesOrder(order);
                    item.setDrugBatch(batch);
                    item.setQuantity(itemDTO.getQuantity());
                    item.setUnitPrice(unitPrice);
                    item.setSubTotal(subTotal);

                    orderItems.add(item);
                }
            } else {
                throw new RuntimeException("Sales Order must contain at least one item");
            }

            order.setTotalAmount(calculatedTotal);
            order.setNetAmount(calculatedTotal - dto.getDiscountAmount());

            // Entity එකේ List එකේ name එක 'salesOrderItems' නම් setSalesOrderItems, 'items' නම් setItems යොදන්න
            order.setSalesOrderItems(orderItems);

            salesOrderRepository.save(order);

        } catch (Exception e) {
            log.error("Error processing sales order: " + e.getMessage());
            throw e;


            }
        }

    @Override
    public List<SalesOrderDTO> getAllSalesOrders() {
        log.info("Fetching all sales orders");
        List<SalesOrder> orders = salesOrderRepository.findAll();
        List<SalesOrderDTO> dtoList = new ArrayList<>();
        for (SalesOrder order : orders) {
            dtoList.add(convertToDTO(order));
        }
        return dtoList;
    }

    @Override
    public SalesOrderDTO getSalesOrderById(Long orderId) {
        log.info("Fetching sales order for ID: {}", orderId);
        Optional<SalesOrder> optional = salesOrderRepository.findById(orderId);
        if (optional.isEmpty()) {
            throw new RuntimeException("Sales Order Not Found for ID: " + orderId);
        }
        return convertToDTO(optional.get());
    }

    private SalesOrderDTO convertToDTO(SalesOrder order) {
        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDiscountAmount(order.getDiscountAmount());
        dto.setNetAmount(order.getNetAmount());
        dto.setPaymentType(order.getPaymentType());

        if (order.getPatient() != null) {
            dto.setPatientId(order.getPatient().getPatientId());
        }
        if (order.getCashier() != null) {
            dto.setCashierId(order.getCashier().getUserId());
        }

        List<SalesOrderItemDTO> itemDTOs = new ArrayList<>();
        if (order.getSalesOrderItems() != null) {
            for (SalesOrderItem item : order.getSalesOrderItems()) {
                SalesOrderItemDTO itemDTO = new SalesOrderItemDTO();
                itemDTO.setOrderItemId(item.getOrderItemId());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setUnitPrice(item.getUnitPrice());
                itemDTO.setSubTotal(item.getSubTotal());

                if (item.getDrugBatch() != null) {
                    itemDTO.setBatchId(item.getDrugBatch().getBatchId());
                }
                itemDTOs.add(itemDTO);
            }
        }
        dto.setSalesOrderItems(itemDTOs);
        return dto;
    }
}
