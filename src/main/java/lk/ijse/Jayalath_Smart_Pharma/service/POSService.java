package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.POSCheckingRequestDTO;

public interface POSService {
    public void processPOSCheckout(POSCheckingRequestDTO requestDTO);
}
