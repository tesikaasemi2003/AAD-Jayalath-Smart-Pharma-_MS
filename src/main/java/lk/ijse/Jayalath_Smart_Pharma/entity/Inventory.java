package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name= "Inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inventoryId;

    @Column(nullable = false)
    private int quantityOnHand;

    private LocalDateTime lastUpdate;

    @OneToOne
    @JoinColumn(name = "batch_id", referencedColumnName = "batchId" ,nullable = false)
    private DrugBatch drugBatch;
}
