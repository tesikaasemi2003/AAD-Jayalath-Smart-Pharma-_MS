package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.PrescriptionDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.PrescriptionItemDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Drug;
import lk.ijse.Jayalath_Smart_Pharma.entity.Prescription;
import lk.ijse.Jayalath_Smart_Pharma.entity.PrescriptionItem;
import lk.ijse.Jayalath_Smart_Pharma.repository.DrugRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.PrescriptionItemRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.PrescriptionRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.PrescriptionItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PrescriptionItemServiceImpl implements PrescriptionItemService {
    private final PrescriptionItemRepository prescriptionItemRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final DrugRepository drugRepository;

    public PrescriptionItemServiceImpl(PrescriptionItemRepository prescriptionItemRepository,
                                       PrescriptionRepository prescriptionRepository,
                                       DrugRepository drugRepository) {
        this.prescriptionItemRepository = prescriptionItemRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.drugRepository = drugRepository;
    }

    @Override
    @Transactional
    public void savePrescriptionItem(PrescriptionItemDTO dto) {
        log.info("Executing savePrescriptionItem method");
        try {
            Optional<Prescription> optionalPrescription = prescriptionRepository.findById(dto.getPrescriptionId());
            if (optionalPrescription.isEmpty()) {
                throw new RuntimeException("Prescription Not Found");
            }

            Optional<Drug> optionalDrug = drugRepository.findById(dto.getDrugId());
            if (optionalDrug.isEmpty()) {
                throw new RuntimeException("Drug Not Found");
            }

            PrescriptionItem item = new PrescriptionItem();
            item.setPrescription(optionalPrescription.get());
            item.setDrug(optionalDrug.get());
            item.setDosage(dto.getDosage());
            item.setFrequency(dto.getFrequency());
            item.setDurationDays(dto.getDurationDays());

            prescriptionItemRepository.save(item);

        } catch (Exception e) {
            log.error("Error in savePrescriptionItem method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<PrescriptionItemDTO> getAllPrescriptionItems() {
        log.info("Executing getAllPrescriptionItems method");
        try {
            List<PrescriptionItem> list = prescriptionItemRepository.findAll();
            List<PrescriptionItemDTO> dtoList = new ArrayList<>();
            for (PrescriptionItem item : list) {
                dtoList.add(convertToDTO(item));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error in getAllPrescriptionItems method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public PrescriptionItemDTO getPrescriptionItemById(Long presItemId) {
        log.info("Executing getPrescriptionItemById method for ID: {}", presItemId);
        try {
            Optional<PrescriptionItem> optional = prescriptionItemRepository.findById(presItemId);
            if (optional.isEmpty()) {
                throw new RuntimeException("Prescription Not Found");
            }
            return convertToDTO(optional.get());
        } catch (Exception e) {
            log.error("Error in getPrescriptionItemById method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<PrescriptionItemDTO> getItemsByPrescriptionId(Long prescriptionId) {
        log.info("Executing getItemsByPrescriptionId for Prescription ID: {}", prescriptionId);
        try {
            List<PrescriptionItem> list = prescriptionItemRepository.findByPrescriptionPrescriptionId(prescriptionId);
            List<PrescriptionItemDTO> dtoList = new ArrayList<>();
            for (PrescriptionItem item : list) {
                dtoList.add(convertToDTO(item));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error in getItemsByPrescriptionId method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void updatePrescriptionItem(Long presItemId, PrescriptionItemDTO dto) {
        log.info("Executing updatePrescriptionItem for ID: {}", presItemId);
        try {
            Optional<PrescriptionItem> optionalItem = prescriptionItemRepository.findById(presItemId);
            if (optionalItem.isEmpty()) {
                throw new RuntimeException("Prescription Not Found");
            }

            Optional<Drug> optionalDrug = drugRepository.findById(dto.getDrugId());
            if (optionalDrug.isEmpty()) {
                throw new RuntimeException("Drug Not Found");
            }

            PrescriptionItem item = optionalItem.get();
            item.setDrug(optionalDrug.get());
            item.setDosage(dto.getDosage());
            item.setFrequency(dto.getFrequency());
            item.setDurationDays(dto.getDurationDays());

            prescriptionItemRepository.save(item);

        } catch (Exception e) {
            log.error("Error in updatePrescriptionItem method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deletePrescriptionItem(Long presItemId) {
        log.info("Executing deletePrescriptionItem for ID: {}", presItemId);
        try {
            Optional<PrescriptionItem> optionalItem = prescriptionItemRepository.findById(presItemId);
            if (optionalItem.isEmpty()) {
                throw new RuntimeException("Prescription Not Found");
            }
            prescriptionItemRepository.deleteById(presItemId);
        } catch (Exception e) {
            log.error("Error in deletePrescriptionItem method: " + e.getMessage());
            throw e;
        }
    }

    private PrescriptionItemDTO convertToDTO(PrescriptionItem item) {
        PrescriptionItemDTO dto = new PrescriptionItemDTO();
        dto.setPresItemId(item.getPresItemId());
        dto.setDosage(item.getDosage());
        dto.setFrequency(item.getFrequency());
        dto.setDurationDays(item.getDurationDays());

        if (item.getPrescription() != null) {
            dto.setPrescriptionId(item.getPrescription().getPrescriptionId());
        }
        if (item.getDrug() != null) {
            dto.setDrugId(item.getDrug().getDrugId());
        }
        return dto;
    }
}
