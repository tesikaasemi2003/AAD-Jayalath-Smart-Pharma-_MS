package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.SupplierDTO;
import lk.ijse.Jayalath_Smart_Pharma.repository.SupplierRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lk.ijse.Jayalath_Smart_Pharma.entity.Supplier;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional
    public void saveSupplier(SupplierDTO supplierDTO) {
        log.info("Executing saveSupplier method");
        try{
            Supplier supplier = new Supplier();
            supplierDTO.setSupplierName(supplierDTO.getSupplierName());
            supplierDTO.setSupplierContactEmail(supplierDTO.getSupplierContactEmail());
            supplierDTO.setSupplierAddress(supplierDTO.getSupplierAddress());
            supplierDTO.setSupplierPhoneNumber(String.valueOf(supplierDTO.getSupplierPhoneNumber()));
            supplierRepository.save(supplier);

        }catch (Exception e){
            log.error("Error in saving User" + e.getMessage());
            throw e;
        }

    }
}
