package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.PurchaseOrderDTO;

import java.util.List;

public interface PurchaseOrderService {
    public void createPurchaseOrder(PurchaseOrderDTO dto);
    public List<PurchaseOrderDTO> getAllPurchaseOrders();
    public PurchaseOrderDTO getPurchaseOrderById(Long poId);
}
