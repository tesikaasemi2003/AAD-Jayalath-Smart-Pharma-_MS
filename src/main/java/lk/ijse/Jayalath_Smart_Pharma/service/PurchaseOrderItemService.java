package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.PurchaseOrderItemDTO;

import java.util.List;

public interface PurchaseOrderItemService {
    public List<PurchaseOrderItemDTO> getAllPurchaseOrderItems();
    public PurchaseOrderItemDTO getPurchaseOrderItemById(Long poItemId);
    public List<PurchaseOrderItemDTO> getItemsByPoId(Long poId);
}
