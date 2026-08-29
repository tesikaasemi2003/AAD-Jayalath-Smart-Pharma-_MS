package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.DrugBatchDTO;

import java.util.List;

public interface DrugBatchService {
    public void saveDrugBatch(DrugBatchDTO drugBatchDTO);
    public List<DrugBatchDTO> getAllDrugBatches();
    public DrugBatchDTO getDrugBatchById(Long batchId);
    public List<DrugBatchDTO> getAvailableBatchesByFEFO(Long drugId);
    public List<DrugBatchDTO> getExpiringBatchesWithinDays(int days);
    public void updateDrugBatch(Long batchId, DrugBatchDTO drugBatchDTO);
    public void deleteDrugBatch(Long batchId);
}
