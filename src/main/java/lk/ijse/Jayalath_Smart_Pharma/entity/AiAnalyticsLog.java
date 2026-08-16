package lk.ijse.Jayalath_Smart_Pharma.entity;

import jakarta.persistence.*;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.analysisType;
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
public class AiAnalyticsLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Enumerated(EnumType.STRING)
    private analysisType analysisType;
    private long aiResponsePayload;
    private LocalDateTime generatedAt;
    private long triggeredBy_userId;
}
