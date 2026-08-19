package lk.ijse.Jayalath_Smart_Pharma.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {
    private Long supplierId;
    private String supplierName;
    private String supplierContactEmail;
    private String supplierPhoneNumber;
    private String supplierAddress;
}
