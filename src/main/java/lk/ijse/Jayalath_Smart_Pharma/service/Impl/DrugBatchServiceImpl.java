package lk.ijse.Jayalath_Smart_Pharma.service.impl;

import lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage;
import lk.ijse.Jayalath_Smart_Pharma.dto.DrugBatchDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Drug;
import lk.ijse.Jayalath_Smart_Pharma.entity.DrugBatch;
import lk.ijse.Jayalath_Smart_Pharma.entity.Inventory;
import lk.ijse.Jayalath_Smart_Pharma.repository.DrugBatchRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.DrugRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.DrugBatchService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DrugBatchServiceImpl implements DrugBatchService {

    private final DrugBatchRepository drugBatchRepository;
    private final DrugRepository drugRepository;

    public DrugBatchServiceImpl(DrugBatchRepository drugBatchRepository, DrugRepository drugRepository) {
        this.drugBatchRepository = drugBatchRepository;
        this.drugRepository = drugRepository;
    }

    @Override
    @Transactional
    public void saveDrugBatch(DrugBatchDTO drugBatchDTO) {
        log.info("Executing saveDrugBatch method for Batch Number: {}", drugBatchDTO.getBatchNumber());
        try {
            Optional<Drug> optionalDrug = drugRepository.findById(drugBatchDTO.getDrugId());
            if (!optionalDrug.isPresent()) {
                throw new RuntimeException("Drug Not Found");
            }

            DrugBatch drugBatch = new DrugBatch();
            drugBatch.setBatchNumber(drugBatchDTO.getBatchNumber());
            drugBatch.setManufactureDate(drugBatchDTO.getManufactureDate());
            drugBatch.setExpiryDate(drugBatchDTO.getExpiryDate());
            drugBatch.setPurchasePrice(drugBatchDTO.getPurchasePrice());
            drugBatch.setSellingPrice(drugBatchDTO.getSellingPrice());
            drugBatch.setDiscountPercentage(drugBatchDTO.getDiscountPercentage());
            drugBatch.setBarcodeString(drugBatchDTO.getBarcodeString());
            drugBatch.setDrug(optionalDrug.get());

            if (drugBatchDTO.getQuantityOnHand() != null) {
                Inventory inventory = new Inventory();
                inventory.setQuantityOnHand(drugBatchDTO.getQuantityOnHand());
                inventory.setDrugBatch(drugBatch);
                drugBatch.setInventory(inventory);
            }

            drugBatchRepository.save(drugBatch);
        } catch (Exception e) {
            log.error("Error in saveDrugBatch method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<DrugBatchDTO> getAllDrugBatches() {
        log.info("Executing getAllDrugBatches method");
        try {
            List<DrugBatch> batches = drugBatchRepository.findAll();
            List<DrugBatchDTO> dtoList = new ArrayList<>();
            for (DrugBatch batch : batches) {
                dtoList.add(convertToDTO(batch));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error in getAllDrugBatches method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public DrugBatchDTO getDrugBatchById(Long batchId) {
        log.info("Executing getDrugBatchById method for ID: {}", batchId);
        try {
            Optional<DrugBatch> optionalBatch = drugBatchRepository.findById(batchId);
            if (!optionalBatch.isPresent()) {
                throw new RuntimeException(ResponseMessage.NOT_FOUND);
            }
            return convertToDTO(optionalBatch.get());
        } catch (Exception e) {
            log.error("Error in getDrugBatchById method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<DrugBatchDTO> getAvailableBatchesByFEFO(Long drugId) {
        log.info("Executing getAvailableBatchesByFEFO for Drug ID: {}", drugId);
        try {
            List<DrugBatch> batches = drugBatchRepository.findAvailableBatchesByFefo(drugId, LocalDate.now());
            List<DrugBatchDTO> dtoList = new ArrayList<>();
            for (DrugBatch batch : batches) {
                dtoList.add(convertToDTO(batch));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error in getAvailableBatchesByFEFO method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<DrugBatchDTO> getExpiringBatchesWithinDays(int days) {
        log.info("Executing getExpiringBatchesWithinDays for next {} days", days);
        try {
            LocalDate today = LocalDate.now();
            LocalDate targetDate = today.plusDays(days);
            List<DrugBatch> batches = drugBatchRepository.findExpiringBatchesWithinDays(today, targetDate);
            List<DrugBatchDTO> dtoList = new ArrayList<>();
            for (DrugBatch batch : batches) {
                dtoList.add(convertToDTO(batch));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error in getExpiringBatchesWithinDays method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void updateDrugBatch(Long batchId, DrugBatchDTO drugBatchDTO) {
        log.info("Executing updateDrugBatch method for ID: {}", batchId);
        try {
            Optional<DrugBatch> optionalBatch = drugBatchRepository.findById(batchId);
            if (!optionalBatch.isPresent()) {
                throw new RuntimeException(ResponseMessage.NOT_FOUND);
            }

            Optional<Drug> optionalDrug = drugRepository.findById(drugBatchDTO.getDrugId());
            if (!optionalDrug.isPresent()) {
                throw new RuntimeException("Drug Not Found");
            }

            DrugBatch drugBatch = optionalBatch.get();
            drugBatch.setBatchNumber(drugBatchDTO.getBatchNumber());
            drugBatch.setManufactureDate(drugBatchDTO.getManufactureDate());
            drugBatch.setExpiryDate(drugBatchDTO.getExpiryDate());
            drugBatch.setPurchasePrice(drugBatchDTO.getPurchasePrice());
            drugBatch.setSellingPrice(drugBatchDTO.getSellingPrice());
            drugBatch.setDiscountPercentage(drugBatchDTO.getDiscountPercentage());
            drugBatch.setBarcodeString(drugBatchDTO.getBarcodeString());
            drugBatch.setDrug(optionalDrug.get());

            if (drugBatch.getInventory() != null && drugBatchDTO.getQuantityOnHand() != null) {
                drugBatch.getInventory().setQuantityOnHand(drugBatchDTO.getQuantityOnHand());
            }

            drugBatchRepository.save(drugBatch);
        } catch (Exception e) {
            log.error("Error in updateDrugBatch method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteDrugBatch(Long batchId) {
        log.info("Executing deleteDrugBatch method for ID: {}", batchId);
        try {
            Optional<DrugBatch> optionalBatch = drugBatchRepository.findById(batchId);
            if (!optionalBatch.isPresent()) {
                throw new RuntimeException(ResponseMessage.NOT_FOUND);
            }
            drugBatchRepository.deleteById(batchId);
        } catch (Exception e) {
            log.error("Error in deleteDrugBatch method: " + e.getMessage());
            throw e;
        }
    }

    private DrugBatchDTO convertToDTO(DrugBatch batch) {
        DrugBatchDTO dto = new DrugBatchDTO();
        dto.setBatchId(batch.getBatchId());
        dto.setBatchNumber(batch.getBatchNumber());
        dto.setManufactureDate(batch.getManufactureDate());
        dto.setExpiryDate(batch.getExpiryDate());
        dto.setPurchasePrice(batch.getPurchasePrice());
        dto.setSellingPrice(batch.getSellingPrice());
        dto.setDiscountPercentage(batch.getDiscountPercentage());
        dto.setBarcodeString(batch.getBarcodeString());

        if (batch.getDrug() != null) {
            dto.setDrugId(batch.getDrug().getDrugId());
        }
        if (batch.getInventory() != null) {
            dto.setQuantityOnHand(batch.getInventory().getQuantityOnHand());
        }

        return dto;
    }
}