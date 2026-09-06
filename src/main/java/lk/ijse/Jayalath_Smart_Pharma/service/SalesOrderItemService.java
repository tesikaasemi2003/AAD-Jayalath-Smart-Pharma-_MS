package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.SalesOrderDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.SalesOrderItemDTO;

import java.util.List;

public interface SalesOrderItemService {
    public List<SalesOrderItemDTO> getAllSalesOrderItems();
    SalesOrderItemDTO getSalesOrderItemById(Long orderItemId);
    List<SalesOrderItemDTO> getItemsByOrderId(Long orderId);
}
