package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.DrugBatchDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Drug;
import lk.ijse.Jayalath_Smart_Pharma.entity.DrugBatch;
import lk.ijse.Jayalath_Smart_Pharma.entity.Inventory;
import lk.ijse.Jayalath_Smart_Pharma.repository.DrugBatchRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.DrugRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.DrugBatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            drugBatchDTO.setBatchNumber(drugBatchDTO.getBatchNumber());
            drugBatchDTO.setManufactureDate(drugBatchDTO.getManufactureDate());
            drugBatchDTO.setExpiryDate(drugBatchDTO.getExpiryDate());
            drugBatchDTO.setPurchasePrice(drugBatchDTO.getPurchasePrice());
            drugBatchDTO.setSellingPrice(drugBatchDTO.getSellingPrice());
            drugBatchDTO.setDiscountPercentage(drugBatchDTO.getDiscountPercentage());
            drugBatchDTO.setBarcodeString(drugBatchDTO.getBarcodeString());
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
