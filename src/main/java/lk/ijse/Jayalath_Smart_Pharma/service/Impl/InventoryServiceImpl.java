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

import java.util.ArrayList;
import java.util.List;
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
        log.info("Executing saveInventory method for Batch ID: {}", inventoryDTO.getBatchId());
        try {
            Optional<DrugBatch> optionalBatch = drugBatchRepository.findById(inventoryDTO.getBatchId());
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

    @Override
    public List<InventoryDTO> getAllInventories() {
        log.info("Executing getAllInventories method");
        try {
            List<Inventory> list = inventoryRepository.findAll();
            List<InventoryDTO> dtoList = new ArrayList<>();
            for (Inventory inventory : list) {
                dtoList.add(convertToDTO(inventory));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error in getAllInventories method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public InventoryDTO getInventoryById(Long inventoryId) {
        log.info("Executing getInventoryById method for ID: {}", inventoryId);
        try {
            Optional<Inventory> optionalInventory = inventoryRepository.findById(inventoryId);
            if (!optionalInventory.isPresent()) {
                throw new RuntimeException("Inventory Not Found");
            }
            return convertToDTO(optionalInventory.get());
        } catch (Exception e) {
            log.error("Error in getInventoryById method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void updateInventory(Long inventoryId, InventoryDTO inventoryDTO) {
        log.info("Executing updateInventory method for ID: {}", inventoryId);
        try {
            Optional<Inventory> optionalInventory = inventoryRepository.findById(inventoryId);
            if (!optionalInventory.isPresent()) {
                throw new RuntimeException("Inventory Not Found");
            }

            Optional<DrugBatch> optionalBatch = drugBatchRepository.findById(inventoryDTO.getBatchId());
            if (!optionalBatch.isPresent()) {
                throw new RuntimeException("Drug Batch Not Found");
            }

            Inventory inventory = optionalInventory.get();
            inventory.setQuantityOnHand(inventoryDTO.getQuantityOnHand());
            inventory.setDrugBatch(optionalBatch.get());

            inventoryRepository.save(inventory);
        } catch (Exception e) {
            log.error("Error in updateInventory method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteInventory(Long inventoryId) {
        log.info("Executing deleteInventory method for ID: {}", inventoryId);
        try {
            Optional<Inventory> optionalInventory = inventoryRepository.findById(inventoryId);
            if (!optionalInventory.isPresent()) {
                throw new RuntimeException("Inventory Not Found");
            }
            inventoryRepository.deleteById(inventoryId);
        } catch (Exception e) {
            log.error("Error in deleteInventory method: " + e.getMessage());
            throw e;
        }
    }
    private InventoryDTO convertToDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setInventoryId(inventory.getInventoryId());
        dto.setQuantityOnHand(inventory.getQuantityOnHand());
        if (inventory.getDrugBatch() != null) {
            dto.setBatchId(inventory.getDrugBatch().getBatchId());
        }
        return dto;
    }
}
