package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.PrescriptionDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.PrescriptionItemDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.*;
import lk.ijse.Jayalath_Smart_Pharma.repository.DoctorRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.DrugRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.PatientRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.PrescriptionRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.PrescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DrugRepository drugRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   PatientRepository patientRepository,
                                   DoctorRepository doctorRepository,
                                   DrugRepository drugRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.drugRepository = drugRepository;
    }
    @Override
    @Transactional
    public void savePrescription(PrescriptionDTO prescriptionDTO) {
        log.info("Executing savePrescription method");
        try {
            Optional<Patient> optionalPatient = patientRepository.findById(prescriptionDTO.getPatientId());
            if (optionalPatient.isEmpty()) {
                throw new RuntimeException("Patient Not Found");
            }

            Optional<Doctor> optionalDoctor = doctorRepository.findById(prescriptionDTO.getDoctorId());
            if (optionalDoctor.isEmpty()) {
                throw new RuntimeException("Doctor Not Found");
            }

            Prescription prescription = new Prescription();
            prescriptionDTO.setPatientId(prescriptionDTO.getPatientId());
            prescriptionDTO.setDoctorId(prescriptionDTO.getDoctorId());
            prescriptionDTO.setIssuedDate(prescriptionDTO.getIssuedDate() != null ?
                    prescriptionDTO.getIssuedDate() : LocalDate.now());
            prescription.setRemarks(prescriptionDTO.getRemarks());

            List<PrescriptionItem> items = new ArrayList<>();
            if (prescriptionDTO.getItems() != null) {
                for (PrescriptionItemDTO itemDTO : prescriptionDTO.getItems()) {
                    Optional<Drug> optionalDrug = drugRepository.findById(itemDTO.getDrugId());
                    if (optionalDrug.isEmpty()) {
                        throw new RuntimeException("Drug Not Found for ID: " + itemDTO.getDrugId());
                    }

                    PrescriptionItem item = new PrescriptionItem();
                    item.setDrug(optionalDrug.get());
                    item.setDosage(itemDTO.getDosage());
                    item.setFrequency(itemDTO.getFrequency());
                    item.setDurationDays(itemDTO.getDurationDays());
                    item.setPrescription(prescription);

                    items.add(item);
                }
            }
            prescription.setItems(items);

            prescriptionRepository.save(prescription);

        } catch (Exception e) {
            log.error("Error in savePrescription method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<PrescriptionDTO> getAllPrescriptions() {
        log.info("Executing getAllPrescriptions method");
        try {
            List<Prescription> prescriptions = prescriptionRepository.findAll();
            List<PrescriptionDTO> dtoList = new ArrayList<>();
            for (Prescription prescription : prescriptions) {
                dtoList.add(convertToDTO(prescription));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error in getAllPrescriptions method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public PrescriptionDTO getPrescriptionById(Long prescriptionId) {
        log.info("Executing getPrescriptionById method for ID: {}", prescriptionId);
        try {
            Optional<Prescription> optional = prescriptionRepository.findById(prescriptionId);
            if (optional.isEmpty()) {
                throw new RuntimeException("Prescription Not Found for ID: " + prescriptionId);
            }
            return convertToDTO(optional.get());
        } catch (Exception e) {
            log.error("Error in getPrescriptionById method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<PrescriptionDTO> getPrescriptionsByPatientId(Long patientId) {
        log.info("Executing getPrescriptionsByPatientId for Patient ID: {}", patientId);
        try {
            List<Prescription> prescriptions = prescriptionRepository.findByPatientPatientId(patientId);
            List<PrescriptionDTO> dtoList = new ArrayList<>();
            for (Prescription prescription : prescriptions) {
                dtoList.add(convertToDTO(prescription));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error in getPrescriptionsByPatientId method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deletePrescription(Long prescriptionId) {
        log.info("Executing deletePrescription for ID: {}", prescriptionId);
        try {
            Optional<Prescription> optional = prescriptionRepository.findById(prescriptionId);
            if (optional.isEmpty()) {
                throw new RuntimeException("Prescription Not Found for ID: " + prescriptionId);
            }
            prescriptionRepository.deleteById(prescriptionId);
        } catch (Exception e) {
            log.error("Error in deletePrescription method: " + e.getMessage());
            throw e;
        }
    }

    private PrescriptionDTO convertToDTO(Prescription prescription) {
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setPrescriptionId(prescription.getPrescriptionId());
        dto.setIssuedDate(prescription.getIssueDate());
        dto.setRemarks(prescription.getRemarks());

        if (prescription.getPatient() != null) {
            dto.setPatientId(prescription.getPatient().getPatientId());
        }
        if (prescription.getDoctor() != null) {
            dto.setDoctorId(prescription.getDoctor().getDoctorId());
        }

        List<PrescriptionItemDTO> itemDTOs = new ArrayList<>();
        if (prescription.getItems() != null) {
            for (PrescriptionItem item : prescription.getItems()) {
                PrescriptionItemDTO itemDTO = new PrescriptionItemDTO();
                itemDTO.setPresItemId(item.getPresItemId());
                itemDTO.setDosage(item.getDosage());
                itemDTO.setFrequency(item.getFrequency());
                itemDTO.setDurationDays(item.getDurationDays());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setInstructions(item.getInstructions());
                if (item.getDrug() != null) {
                    itemDTO.setDrugId(item.getDrug().getDrugId());
                }
                itemDTOs.add(itemDTO);
            }
        }
        dto.setItems(itemDTOs);
        return dto;
    }
}
