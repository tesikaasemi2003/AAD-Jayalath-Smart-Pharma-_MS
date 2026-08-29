package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.DrugBatchDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.DrugBatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/drug-batches")
public class DrugBatchController {
     private final DrugBatchService drugBatchService;

     public DrugBatchController(DrugBatchService drugBatchService){
         this.drugBatchService = drugBatchService;
     }

    @PostMapping("/saveDrugBatchs")
    public CommonResponse saveDrugBatch(@RequestBody DrugBatchDTO drugBatchDTO) {
        drugBatchService.saveDrugBatch(drugBatchDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
    @GetMapping
    public CommonResponse getAllDrugBatches() {
        List<DrugBatchDTO> batchList = drugBatchService.getAllDrugBatches();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, batchList);
    }

    @GetMapping("getDrugBatchByIDs/{batchId}")
    public CommonResponse getDrugBatchById(@PathVariable Long batchId) {
        DrugBatchDTO drugBatchDTO = drugBatchService.getDrugBatchById(batchId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, drugBatchDTO);
    }

    @GetMapping("/fefo/{drugId}")
    public CommonResponse getAvailableBatchesByFEFO(@PathVariable Long drugId) {
        List<DrugBatchDTO> batchList = drugBatchService.getAvailableBatchesByFEFO(drugId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, batchList);
    }

    @GetMapping("/expiring-risk")
    public CommonResponse getExpiringBatchesWithinDays(@RequestParam(defaultValue = "30") int days) {
        List<DrugBatchDTO> batchList = drugBatchService.getExpiringBatchesWithinDays(days);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, batchList);
    }

    @PutMapping("updateDrugBatches/{batchId}")
    public CommonResponse updateDrugBatch(@PathVariable Long batchId, @RequestBody DrugBatchDTO drugBatchDTO) {
        drugBatchService.updateDrugBatch(batchId, drugBatchDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping("deleteDrugBatches/{batchId}")
    public CommonResponse deleteDrugBatch(@PathVariable Long batchId) {
        drugBatchService.deleteDrugBatch(batchId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
