package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.SalesOrderDTO;

import java.util.List;

public interface SalesOrderService {
    public void processSalesOrder(SalesOrderDTO dto);
    public List<SalesOrderDTO> getAllSalesOrders();
    public SalesOrderDTO getSalesOrderById(Long orderId);
}
