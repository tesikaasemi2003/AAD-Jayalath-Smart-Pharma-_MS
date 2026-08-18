package lk.ijse.Jayalath_Smart_Pharma.repository;

import lk.ijse.Jayalath_Smart_Pharma.entity.DrugBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugBatchRepository extends JpaRepository<DrugBatch,Long> {
}
