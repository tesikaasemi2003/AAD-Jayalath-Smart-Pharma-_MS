package lk.ijse.Jayalath_Smart_Pharma.controller;


import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.PrescriptionDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.PrescriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/prescriptions")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping("/savePrescriptions")
    public CommonResponse savePrescription(@RequestBody PrescriptionDTO prescriptionDTO) {
        prescriptionService.savePrescription(prescriptionDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @GetMapping("/getAllPrescriptions")
    public CommonResponse getAllPrescriptions() {
        List<PrescriptionDTO> list = prescriptionService.getAllPrescriptions();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }

    @GetMapping("/{prescriptionId}")
    public CommonResponse getPrescriptionById(@PathVariable Long prescriptionId) {
        PrescriptionDTO dto = prescriptionService.getPrescriptionById(prescriptionId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, dto);
    }

    @GetMapping("/patient/{patientId}")
    public CommonResponse getPrescriptionsByPatientId(@PathVariable Long patientId) {
        List<PrescriptionDTO> list = prescriptionService.getPrescriptionsByPatientId(patientId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }



}
