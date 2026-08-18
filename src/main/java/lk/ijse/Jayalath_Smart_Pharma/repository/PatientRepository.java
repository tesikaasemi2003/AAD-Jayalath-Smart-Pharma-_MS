package lk.ijse.Jayalath_Smart_Pharma.repository;

import lk.ijse.Jayalath_Smart_Pharma.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
}
