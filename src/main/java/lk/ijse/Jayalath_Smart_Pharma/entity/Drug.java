package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "drug", cascade = CascadeType.ALL)
    private List<DrugBatch> batches;
}
