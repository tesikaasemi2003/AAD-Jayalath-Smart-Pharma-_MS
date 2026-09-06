package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.POSCartItemDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.POSCheckingRequestDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.*;
import lk.ijse.Jayalath_Smart_Pharma.repository.DrugBatchRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.PatientRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.SalesOrderRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.UserRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.POSService;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.paymentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class POSServiceImpl implements POSService {
    private final SalesOrderRepository salesOrderRepository;
    private final DrugBatchRepository drugBatchRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public POSServiceImpl(SalesOrderRepository salesOrderRepository,
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
    public void processPOSCheckout(POSCheckingRequestDTO requestDTO) {
        log.info("Processing POS Cart Checkout");
        try {
            SalesOrder order = new SalesOrder();
            order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

            if (requestDTO.getPatientId() != null && requestDTO.getPatientId() > 0) {
                Optional<Patient> optionalPatient = patientRepository.findById(requestDTO.getPatientId());
                optionalPatient.ifPresent(order::setPatient);
            }

            if (requestDTO.getUserId() != null && requestDTO.getUserId() > 0) {
                Optional<User> optionalCashier = userRepository.findById(requestDTO.getUserId());
                optionalCashier.ifPresent(order::setCashier);
            }

            order.setOrderDate(LocalDateTime.now());

            // String අගය Enum (paymentType) එකට Convert කිරීම
            if (requestDTO.getPaymentType() != null) {
                try {
                    order.setPaymentType(paymentType.valueOf(requestDTO.getPaymentType().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid Payment Type: " + requestDTO.getPaymentType());
                }
            }

            double overallDiscount = requestDTO.getDiscount() != null ? requestDTO.getDiscount() : 0.0;
            order.setDiscountAmount(overallDiscount);

            double calculatedTotal = 0.0;
            List<SalesOrderItem> orderItems = new ArrayList<>();

            if (requestDTO.getCartItems() != null && !requestDTO.getCartItems().isEmpty()) {
                for (POSCartItemDTO itemDTO : requestDTO.getCartItems()) {
                    Optional<DrugBatch> optionalBatch = drugBatchRepository.findById(itemDTO.getBatchId());
                    if (optionalBatch.isEmpty()) {
                        throw new RuntimeException("Drug Batch Not Found for ID: " + itemDTO.getBatchId());
                    }

                    DrugBatch batch = optionalBatch.get();
                    Inventory inventory = batch.getInventory();

                    if (inventory == null) {
                        throw new RuntimeException("Inventory record not found for Batch ID: " + batch.getBatchId());
                    }

                    // Inventory Stock Check
                    if (inventory.getQuantityOnHand() < itemDTO.getQty()) {
                        throw new RuntimeException("Insufficient stock in Batch ID: " + batch.getBatchId() +
                                " (Available: " + inventory.getQuantityOnHand() + ")");
                    }

                    // Deduct Stock
                    inventory.setQuantityOnHand(inventory.getQuantityOnHand() - itemDTO.getQty());
                    inventory.setLastUpdate(LocalDateTime.now());

                    double unitPrice = itemDTO.getUnitPrice() != null ? itemDTO.getUnitPrice() :
                            (batch.getSellingPrice() != null ? batch.getSellingPrice() : 0.0);
                    double itemDiscount = itemDTO.getDiscountApplied() != null ? itemDTO.getDiscountApplied() : 0.0;
                    double subTotal = (unitPrice * itemDTO.getQty()) - itemDiscount;

                    calculatedTotal += subTotal;

                    SalesOrderItem item = new SalesOrderItem();
                    item.setSalesOrder(order);
                    item.setDrugBatch(batch);
                    item.setQuantity(itemDTO.getQty());
                    item.setUnitPrice(unitPrice);
                    item.setSubTotal(subTotal);

                    orderItems.add(item);
                }
            } else {
                throw new RuntimeException("POS Cart cannot be empty for checkout");
            }

            order.setTotalAmount(calculatedTotal);
            order.setNetAmount(calculatedTotal - overallDiscount);

            // SalesOrder Entity එකේ items/salesOrderItems field නම අනුව සකසන්න
            order.setSalesOrderItems(orderItems);

            salesOrderRepository.save(order);

        } catch (Exception e) {
            log.error("Error during POS checkout process: " + e.getMessage());
            throw e;
        }
    }
}
