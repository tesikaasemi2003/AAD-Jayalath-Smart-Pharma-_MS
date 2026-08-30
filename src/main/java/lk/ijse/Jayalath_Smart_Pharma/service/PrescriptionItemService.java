package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.PrescriptionItemDTO;

import java.util.List;

public interface PrescriptionItemService {
    public void savePrescriptionItem(PrescriptionItemDTO dto);

    public List<PrescriptionItemDTO> getAllPrescriptionItems();

    public PrescriptionItemDTO getPrescriptionItemById(Long presItemId);

    public List<PrescriptionItemDTO> getItemsByPrescriptionId(Long prescriptionId);

    public void updatePrescriptionItem(Long presItemId, PrescriptionItemDTO dto);

    public void deletePrescriptionItem(Long presItemId);
}