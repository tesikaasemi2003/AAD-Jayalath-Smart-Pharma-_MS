package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.DrugBatchDTO;

import java.util.List;

public interface DrugBatchService {
    public void saveDrugBatch(DrugBatchDTO drugBatchDTO);
    public List<DrugBatchDTO> getAllDrugBatches();
}
