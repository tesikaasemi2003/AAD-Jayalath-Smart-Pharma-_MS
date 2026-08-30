package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.PrescriptionDTO;

import java.util.List;

public interface PrescriptionService {
    public void savePrescription(PrescriptionDTO prescriptionDTO);
    public List<PrescriptionDTO> getAllPrescriptions();
    public PrescriptionDTO getPrescriptionById(Long prescriptionId);
    public List<PrescriptionDTO> getPrescriptionsByPatientId(Long patientId);
    public void deletePrescription(Long prescriptionId);
    }
