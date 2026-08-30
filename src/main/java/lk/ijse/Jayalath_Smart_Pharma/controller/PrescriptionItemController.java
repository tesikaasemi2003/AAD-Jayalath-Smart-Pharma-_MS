package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.PrescriptionItemDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.PrescriptionItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/PrescriptionItems")
public class PrescriptionItemController {
    private final PrescriptionItemService prescriptionItemService;
    public PrescriptionItemController(PrescriptionItemService prescriptionItemService) {
        this.prescriptionItemService = prescriptionItemService;
    }

    @PostMapping("/savePrescriptionItems")
    public CommonResponse savePrescriptionItem(@RequestBody PrescriptionItemDTO dto) {
        prescriptionItemService.savePrescriptionItem(dto);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
    @GetMapping("/getAllPrescriptionItems")
    public CommonResponse getAllPrescriptionItems() {
        List<PrescriptionItemDTO> list = prescriptionItemService.getAllPrescriptionItems();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }

    @GetMapping("getPrescriptionItemByIDs/{presItemId}")
    public CommonResponse getPrescriptionItemById(@PathVariable Long presItemId) {
        PrescriptionItemDTO dto = prescriptionItemService.getPrescriptionItemById(presItemId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, dto);
    }

    @GetMapping("/getItemsByPrescriptionIDs/{prescriptionId}")
    public CommonResponse getItemsByPrescriptionId(@PathVariable Long prescriptionId) {
        List<PrescriptionItemDTO> list = prescriptionItemService.getItemsByPrescriptionId(prescriptionId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }

    @PutMapping("updatePrescriptionItems/{presItemId}")
    public CommonResponse updatePrescriptionItem(@PathVariable Long presItemId, @RequestBody PrescriptionItemDTO dto) {
        prescriptionItemService.updatePrescriptionItem(presItemId, dto);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping("deletePrescriptionItems/{presItemId}")
    public CommonResponse deletePrescriptionItem(@PathVariable Long presItemId) {
        prescriptionItemService.deletePrescriptionItem(presItemId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

}
