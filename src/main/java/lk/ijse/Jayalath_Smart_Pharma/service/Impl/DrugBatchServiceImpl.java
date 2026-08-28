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

}
