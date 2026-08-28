package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.DrugDTO;

import java.util.List;

public interface DrugService {
    public void saveDrug(DrugDTO drugDTO);
    public List<DrugDTO> getAllDrugs();
    public DrugDTO getDrugById(Long drugId);
    public void updateDrug(Long drugId, DrugDTO drugDTO);
    public void deleteDrug(Long drugId);
}
