package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.POSCheckingRequestDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.POSService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/pos")
public class POSController {
    private final POSService posService;
    public POSController(POSService posService) {
        this.posService = posService;
    }

    @PostMapping("pos/checkout")
    public CommonResponse processCheckout(@RequestBody POSCheckingRequestDTO requestDTO) {
        posService.processPOSCheckout(requestDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
