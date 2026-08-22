package lk.ijse.Jayalath_Smart_Pharma.repository;

import lk.ijse.Jayalath_Smart_Pharma.entity.DrugBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DrugBatchRepository extends JpaRepository<DrugBatch,Long> {

    // 1. FEFO (First Expiring, First Out) Logic
    @Query("SELECT b FROM DrugBatch b JOIN Inventory i ON b.batchId = i.drugBatch.batchId " +
            "WHERE b.drug.drugId = :drugId AND i.quantityOnHand > 0 AND b.expiryDate > :today " +
            "ORDER BY b.expiryDate ASC")
    List<DrugBatch> findAvailableBatchesByFefo(@Param("drugId") Long drugId, @Param("today") LocalDate today);

    // 2. AI Expiry Risk Analysis Logic (ළඟදීම Expire වන Stock එකේ ඇති Batches)
    @Query("SELECT b FROM DrugBatch b JOIN Inventory i ON b.batchId = i.drugBatch.batchId " +
            "WHERE b.expiryDate BETWEEN :today AND :targetDate AND i.quantityOnHand > 0 " +
            "ORDER BY b.expiryDate ASC")
    List<DrugBatch> findExpiringBatchesWithinDays(@Param("today") LocalDate today, @Param("targetDate") LocalDate targetDate);
}

