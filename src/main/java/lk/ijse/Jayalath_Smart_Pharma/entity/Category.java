package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long categoryId;

    @Column(nullable = false)
   private String categoryName;

   private String categoryDescription;

   @OneToMany(mappedBy= "category", cascade = CascadeType.ALL)
    private List<Drug> drugs;
}
