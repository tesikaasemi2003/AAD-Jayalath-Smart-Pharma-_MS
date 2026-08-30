package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.PatientDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("api/v1/patients")
public class PatientController {
    private PatientService patientService;
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/savePatients")
    public CommonResponse savePatient(@RequestBody PatientDTO patientDTO) {
        patientService.savePatient(patientDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
    @GetMapping("/getAllPatients")
    public CommonResponse getAllPatients() {
        List<PatientDTO> list = patientService.getAllPatients();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }

    @GetMapping("getPatientIDs/{patientId}")
    public CommonResponse getPatientById(@PathVariable Long patientId) {
        PatientDTO dto = patientService.getPatientById(patientId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, dto);
    }

    @PutMapping("updatePatients/{patientId}")
    public CommonResponse updatePatient(@PathVariable Long patientId, @RequestBody PatientDTO patientDTO) {
        patientService.updatePatient(patientId, patientDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping("deletePatients/{patientId}")
    public CommonResponse deletePatient(@PathVariable Long patientId) {
        patientService.deletePatient(patientId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
