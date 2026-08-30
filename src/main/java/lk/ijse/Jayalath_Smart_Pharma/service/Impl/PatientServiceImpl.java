package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.DoctorDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.PatientDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Patient;
import lk.ijse.Jayalath_Smart_Pharma.repository.PatientRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    @Override
    @Transactional
    public void savePatient(PatientDTO patientDTO) {
        log.info("Executing savePatient method for Name: {}", patientDTO.getPatientName());
        try {
            Patient patient = new Patient();
            patientDTO.setPatientName(patientDTO.getPatientName());
            patientDTO.setPatientPhone(patientDTO.getPatientPhone());
            patientDTO.setPatientEmail(patientDTO.getPatientEmail());
            patientDTO.setNicOrPassport(patientDTO.getNicOrPassport());
            patientRepository.save(patient);
        } catch (Exception e) {
            log.error("Error in savePatient method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        log.info("Executing getAllPatients method");
        try {
            List<Patient> patients = patientRepository.findAll();
            List<PatientDTO> dtoList = new ArrayList<>();
            for (Patient patient : patients) {
                dtoList.add(convertToDTO(patient));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error in getAllPatients method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public PatientDTO getPatientById(Long patientId) {
        log.info("Executing getPatientById method for ID: {}", patientId);
        try {
            Optional<Patient> optionalPatient = patientRepository.findById(patientId);
            if (optionalPatient.isEmpty()) {
                throw new RuntimeException("Patient with ID: " + patientId + " not found");
            }
            return convertToDTO(optionalPatient.get());
        } catch (Exception e) {
            log.error("Error in getPatientById method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void updatePatient(Long patientId, PatientDTO patientDTO) {
        log.info("Executing updatePatient method for ID: {}", patientId);
        try {
            Optional<Patient> optionalPatient = patientRepository.findById(patientId);
            if (optionalPatient.isEmpty()) {
                throw new RuntimeException("Patient with ID: " + patientId + " not found");
            }

            Patient patient = optionalPatient.get();
            patientDTO.setPatientName(patientDTO.getPatientName());
            patientDTO.setPatientPhone(patientDTO.getPatientPhone());
            patientDTO.setPatientEmail(patientDTO.getPatientEmail());
            patientDTO.setNicOrPassport(patientDTO.getNicOrPassport());
            patientRepository.save(patient);
        } catch (Exception e) {
            log.error("Error in updatePatient method: " + e.getMessage());
            throw e;
        }
    }

    private PatientDTO convertToDTO(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setPatientId(patient.getPatientId());
        dto.setPatientName(patient.getPatientName());
        dto.setPatientPhone(patient.getPatientPhone());
        dto.setPatientEmail(patient.getPatientEmail());
        dto.setNicOrPassport(patient.getNicOrPassport());
        return dto;
    }

}

