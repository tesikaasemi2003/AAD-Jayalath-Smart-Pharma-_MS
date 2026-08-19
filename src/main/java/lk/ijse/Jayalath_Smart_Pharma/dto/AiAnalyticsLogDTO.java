package lk.ijse.Jayalath_Smart_Pharma.dto;

import jakarta.persistence.*;
import lk.ijse.Jayalath_Smart_Pharma.entity.User;
import lk.ijse.Jayalath_Smart_Pharma.enumaration.analysisType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiAnalyticsLogDTO {
    private long Id;
    private analysisType analysisType;
    private long aiResponsePayload;
    private LocalDateTime generatedAt;
    private User triggeredBy;
}
