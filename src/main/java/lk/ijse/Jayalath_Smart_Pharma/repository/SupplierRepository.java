package lk.ijse.Jayalath_Smart_Pharma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lk.ijse.Jayalath_Smart_Pharma.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
}
