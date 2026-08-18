package lk.ijse.Jayalath_Smart_Pharma.repository;

import lk.ijse.Jayalath_Smart_Pharma.entity.AiAnalyticsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiAnalyticsLogRepository extends JpaRepository<AiAnalyticsLog,Long> {
}
