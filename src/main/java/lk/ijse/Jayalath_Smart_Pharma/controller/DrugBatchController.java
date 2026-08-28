package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.DrugBatchDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.DrugBatchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/drug-batches")
public class DrugBatchController {
     private final DrugBatchService drugBatchService;

     public DrugBatchController(DrugBatchService drugBatchService){
         this.drugBatchService = drugBatchService;
     }

    @PostMapping
    public CommonResponse saveDrugBatch(@RequestBody DrugBatchDTO drugBatchDTO) {
        drugBatchService.saveDrugBatch(drugBatchDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
