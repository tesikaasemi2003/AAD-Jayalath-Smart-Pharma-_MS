package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.PurchaseOrderDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.PurchaseOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping("/createPurchaseOrders")
    public CommonResponse createPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        purchaseOrderService.createPurchaseOrder(purchaseOrderDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @GetMapping("/getAllPurchaseOrders")
    public CommonResponse getAllPurchaseOrders() {
        List<PurchaseOrderDTO> list = purchaseOrderService.getAllPurchaseOrders();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }

    @GetMapping("getPurchaseOrderById/{poId}")
    public CommonResponse getPurchaseOrderById(@PathVariable Long poId) {
        PurchaseOrderDTO dto = purchaseOrderService.getPurchaseOrderById(poId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, dto);
    }
}
