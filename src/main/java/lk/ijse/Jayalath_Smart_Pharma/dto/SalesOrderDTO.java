package lk.ijse.Jayalath_Smart_Pharma.dto;

import jakarta.persistence.*;
import lk.ijse.Jayalath_Smart_Pharma.entity.Patient;
import lk.ijse.Jayalath_Smart_Pharma.entity.SalesOrderItem;
import lk.ijse.Jayalath_Smart_Pharma.entity.User;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.paymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderDTO {
    private long orderId;
    private String orderNumber;
    private Patient patient;
    private User cashier;
    private LocalDateTime orderDate;
    private double totalAmount;
    private double discountAmount;
    private double netAmount;
    private paymentType paymentType;
    private List<SalesOrderItem> items;
}
