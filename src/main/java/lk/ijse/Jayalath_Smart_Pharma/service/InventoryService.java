package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {
    public void saveInventory(InventoryDTO inventoryDTO);
    public List<InventoryDTO> getAllInventories();
    public InventoryDTO getInventoryById(Long inventoryId);
    public void updateInventory(Long inventoryId, InventoryDTO inventoryDTO);
    public void deleteInventory(Long inventoryId);
}
