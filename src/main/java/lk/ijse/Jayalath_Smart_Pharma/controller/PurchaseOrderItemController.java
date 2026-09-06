package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.PurchaseOrderItemDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.PurchaseOrderItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/purchase-order-items")
public class PurchaseOrderItemController {

    private final PurchaseOrderItemService purchaseOrderItemService;
    public PurchaseOrderItemController(PurchaseOrderItemService purchaseOrderItemService) {
        this.purchaseOrderItemService = purchaseOrderItemService;
    }

    @GetMapping("/getAllPurchaseOrderItems")
    public CommonResponse getAllPurchaseOrderItems() {
        List<PurchaseOrderItemDTO> list = purchaseOrderItemService.getAllPurchaseOrderItems();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }

    @GetMapping("getPOById/{poItemId}")
    public CommonResponse getPurchaseOrderItemById(@PathVariable Long poItemId) {
        PurchaseOrderItemDTO dto = purchaseOrderItemService.getPurchaseOrderItemById(poItemId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, dto);
    }

    @GetMapping("/po/{poId}")
    public CommonResponse getItemsByPoId(@PathVariable Long poId) {
        List<PurchaseOrderItemDTO> list = purchaseOrderItemService.getItemsByPoId(poId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }
}
