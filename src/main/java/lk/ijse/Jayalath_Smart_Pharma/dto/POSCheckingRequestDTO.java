package lk.ijse.Jayalath_Smart_Pharma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class POSCheckingRequestDTO {
    private Long userId;
    private Long patientId;
    private String paymentType;
    private Double totalAmount;
    private Double discount;
    private List<POSCartItemDTO> cartItems;
}
