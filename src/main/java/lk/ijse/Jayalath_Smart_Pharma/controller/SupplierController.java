package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.SupplierDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    private SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping("/saveSuppliers")
    public CommonResponse saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        supplierService.saveSupplier(supplierDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @GetMapping("/getAllSuppliers")
    public CommonResponse getAllSuppliers() {
        List<SupplierDTO> supplierList = supplierService.getAllSuppliers();
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, supplierList);
    }

    @GetMapping("getSupplierById/{supplierId}")
    public CommonResponse getSupplierById(@PathVariable Long supplierId) {
        SupplierDTO supplierDTO = supplierService.getSupplierById(supplierId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE, supplierDTO);
    }

    @PutMapping("updateSuppliers/{supplierId}")
    public CommonResponse updateSupplier(@PathVariable Long supplierId, @RequestBody SupplierDTO supplierDTO) {
        supplierService.updateSupplier(supplierId, supplierDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping("deleteSuppliers/{supplierId}")
    public CommonResponse deleteSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }


}