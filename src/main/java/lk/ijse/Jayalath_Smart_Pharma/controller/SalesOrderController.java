package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.SalesOrderDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.SalesOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/sales")
public class SalesOrderController {
    private SalesOrderService salesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @PostMapping("/checkout")
    public CommonResponse processSalesOrder(@RequestBody SalesOrderDTO salesOrderDTO) {
        salesOrderService.processSalesOrder(salesOrderDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @GetMapping("/getAllSalesOrders")
    public CommonResponse getAllSalesOrders() {
        List<SalesOrderDTO> list = salesOrderService.getAllSalesOrders();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }

    @GetMapping("/{orderId}")
    public CommonResponse getSalesOrderById(@PathVariable Long orderId) {
        SalesOrderDTO dto = salesOrderService.getSalesOrderById(orderId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, dto);
    }
}
