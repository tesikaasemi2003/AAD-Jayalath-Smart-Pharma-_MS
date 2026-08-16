package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.paymentType;

import java.time.LocalDateTime;

public class SalesOrder {
    private long orderId;
    private String orderNumber;
    private long patientId;
    private long cashierId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private double discountAmount;
    private double netAmount;
    @Enumerated(EnumType.STRING)
    private paymentType paymentType;
}
