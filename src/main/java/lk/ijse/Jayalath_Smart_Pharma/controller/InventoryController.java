package lk.ijse.Jayalath_Smart_Pharma.controller;


import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.InventoryDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryController {
    private InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/saveInventories")
    public CommonResponse saveInventory(@RequestBody InventoryDTO inventoryDTO) {
        inventoryService.saveInventory(inventoryDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @GetMapping("/getAllInventories")
    public CommonResponse getAllInventories() {
        List<InventoryDTO> list = inventoryService.getAllInventories();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }

    @GetMapping("getInventoryByIDs/{inventoryId}")
    public CommonResponse getInventoryById(@PathVariable Long inventoryId) {
        InventoryDTO dto = inventoryService.getInventoryById(inventoryId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, dto);
    }
    @PutMapping("updateInventories/{inventoryId}")
    public CommonResponse updateInventory(@PathVariable Long inventoryId, @RequestBody InventoryDTO inventoryDTO) {
        inventoryService.updateInventory(inventoryId, inventoryDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping("deleteInventories/{inventoryId}")
    public CommonResponse deleteInventory(@PathVariable Long inventoryId) {
        inventoryService.deleteInventory(inventoryId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
