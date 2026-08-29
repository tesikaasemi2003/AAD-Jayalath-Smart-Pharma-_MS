package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.InventoryDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.DrugBatch;
import lk.ijse.Jayalath_Smart_Pharma.entity.Inventory;
import lk.ijse.Jayalath_Smart_Pharma.repository.DrugBatchRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.InventoryRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final DrugBatchRepository drugBatchRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, DrugBatchRepository drugBatchRepository) {
        this.inventoryRepository = inventoryRepository;
        this.drugBatchRepository = drugBatchRepository;
    }

    @Override
    @Transactional
    public void saveInventory(InventoryDTO inventoryDTO) {
        log.info("Executing saveInventory method for Batch ID: {}", inventoryDTO.getDrugBatch().getBatchId());
        try {
            Optional<DrugBatch> optionalBatch = drugBatchRepository.findById(inventoryDTO.getDrugBatch().getBatchId());
            if (!optionalBatch.isPresent()) {
                throw new RuntimeException("Drug Batch Not Found");
            }

            Inventory inventory = new Inventory();
            inventory.setQuantityOnHand(inventoryDTO.getQuantityOnHand());
            inventory.setDrugBatch(optionalBatch.get());

            inventoryRepository.save(inventory);
        } catch (Exception e) {
            log.error("Error in saveInventory method: " + e.getMessage());
            throw e;
        }
    }
}
