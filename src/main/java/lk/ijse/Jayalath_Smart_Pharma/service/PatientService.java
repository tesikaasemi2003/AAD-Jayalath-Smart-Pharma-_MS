package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.PatientDTO;

import java.util.List;

public interface PatientService {
    public void savePatient(PatientDTO patientDTO);
    public List<PatientDTO> getAllPatients();
    public PatientDTO getPatientById(Long patientId);
    public void updatePatient(Long patientId, PatientDTO patientDTO);
}
