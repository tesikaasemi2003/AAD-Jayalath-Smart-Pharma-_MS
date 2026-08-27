package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.DrugDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.DrugService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/drugs")
public class DrugController {
    private final DrugService drugService;

    public DrugController(DrugService drugService){
        this.drugService = drugService;
    }
    @PostMapping("/saveDrugs")
    public CommonResponse saveDrug(@RequestBody DrugDTO drugDTO) {
        drugService.saveDrug(drugDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
