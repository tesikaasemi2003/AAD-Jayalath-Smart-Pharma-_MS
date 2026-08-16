package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.*;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.paymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "sales_orders")
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column(unique = true , nullable = false )
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "patient_id" , nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "cashier_id")
    private User cashier;


    private LocalDateTime orderDate;
    private double totalAmount;
    private double discountAmount;
    private double netAmount;
    @Enumerated(EnumType.STRING)
    private paymentType paymentType;

    @OneToMany(mappedBy = "salesOrder" , cascade = CascadeType.ALL)
    private List<SalesOrderItem> items;
}
