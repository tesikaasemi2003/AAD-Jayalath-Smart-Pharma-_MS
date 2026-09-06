package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.SalesOrderItemDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.SalesOrderItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/sales-order-items")
public class SalesOrderItemController {
    private SalesOrderItemService salesOrderItemService;
    public SalesOrderItemController(SalesOrderItemService salesOrderItemService) {
        this.salesOrderItemService = salesOrderItemService;
    }

    @GetMapping("/getAllSalesOrderItems")
    public CommonResponse getAllSalesOrderItems() {
        List<SalesOrderItemDTO> list = salesOrderItemService.getAllSalesOrderItems();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }

    @GetMapping("getItemById/{orderItemId}")
    public CommonResponse getSalesOrderItemById(@PathVariable Long orderItemId) {
        SalesOrderItemDTO dto = salesOrderItemService.getSalesOrderItemById(orderItemId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, dto);
    }

    @GetMapping("/order/{orderId}")
    public CommonResponse getItemsByOrderId(@PathVariable Long orderId) {
        List<SalesOrderItemDTO> list = salesOrderItemService.getItemsByOrderId(orderId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }
}
