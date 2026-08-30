package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.DoctorDTO;
import lk.ijse.Jayalath_Smart_Pharma.dto.PatientDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    private DoctorService doctorService;
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/saveDoctors")
    public CommonResponse saveDoctor(@RequestBody DoctorDTO doctorDTO) {
        doctorService.saveDoctor(doctorDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @GetMapping("/getAlldoctors")
    public CommonResponse getAllDoctors() {
        List<DoctorDTO> list = doctorService.getAllDoctors();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, list);
    }

    @GetMapping("getDoctorByIDs/{doctorId}")
    public CommonResponse getDoctorById(@PathVariable Long doctorId) {
        DoctorDTO dto = doctorService.getDoctorById(doctorId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, dto);
    }

    @PutMapping("updateDoctors/{doctorId}")
    public CommonResponse updateDoctor(@PathVariable Long doctorId, @RequestBody DoctorDTO doctorDTO) {
        doctorService.updateDoctor(doctorId, doctorDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping("deleteDoctors/{doctorId}")
    public CommonResponse deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
