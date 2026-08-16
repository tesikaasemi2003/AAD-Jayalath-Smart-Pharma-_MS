package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="drugs")
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long drugId;

    @Column(nullable=false)
    private String brandName;
    private String genericName;


    @Column(nullable=false)
    private int reorderLevel;
    private String unit;


}
