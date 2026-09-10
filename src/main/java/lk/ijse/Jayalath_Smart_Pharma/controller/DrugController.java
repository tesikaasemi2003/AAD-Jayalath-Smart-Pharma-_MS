package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.DrugDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.DrugService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/drugs")
public class DrugController {

    private final DrugService drugService;

    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @PostMapping
    public CommonResponse saveDrug(@RequestBody DrugDTO drugDTO) {
        drugService.saveDrug(drugDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @GetMapping
    public CommonResponse getAllDrugs() {
        List<DrugDTO> drugList = drugService.getAllDrugs();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, drugList);
    }

    @GetMapping("/{drugId}")
    public CommonResponse getDrugById(@PathVariable Long drugId) {
        DrugDTO drugDTO = drugService.getDrugById(drugId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, drugDTO);
    }

    @PutMapping("/{drugId}")
    public CommonResponse updateDrug(@PathVariable Long drugId, @RequestBody DrugDTO drugDTO) {
        drugService.updateDrug(drugId, drugDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping("/{drugId}")
    public CommonResponse deleteDrug(@PathVariable Long drugId) {
        drugService.deleteDrug(drugId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}