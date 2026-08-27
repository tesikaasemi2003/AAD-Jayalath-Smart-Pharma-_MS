package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.SupplierDTO;
import lk.ijse.Jayalath_Smart_Pharma.repository.SupplierRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lk.ijse.Jayalath_Smart_Pharma.entity.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        try {
            Supplier supplier = new Supplier();
            supplierDTO.setSupplierName(supplierDTO.getSupplierName());
            supplierDTO.setSupplierContactEmail(supplierDTO.getSupplierContactEmail());
            supplierDTO.setSupplierAddress(supplierDTO.getSupplierAddress());
            supplierDTO.setSupplierPhoneNumber(String.valueOf(supplierDTO.getSupplierPhoneNumber()));
            supplierRepository.save(supplier);

        } catch (Exception e) {
            log.error("Error in saving User" + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        log.info("Executing getAllSuppliers method");
        try {
            List<Supplier> suppliers = supplierRepository.findAll();
            List<SupplierDTO> supplierDTOs = new ArrayList<>();
            for (Supplier supplier : suppliers) {
                SupplierDTO supplierDTO = new SupplierDTO();
                supplierDTO.setSupplierId(supplier.getSupplierId());
                supplierDTO.setSupplierName(supplier.getSupplierName());
                supplierDTO.setSupplierContactEmail(supplier.getSupplierContactEmail());
                supplierDTO.setSupplierAddress(supplier.getSupplierAddress());
                supplierDTO.setSupplierPhoneNumber(supplier.getSupplierPhoneNumber());
                supplierDTOs.add(supplierDTO);
            }
            return supplierDTOs;

        } catch (Exception e) {
            log.error("Error in getAllSuppliers method: " + e.getMessage());
            throw e;
        }
    }
    @Override
    public SupplierDTO getSupplierById(Long supplierId) {
        log.info("Executing getSupplierById method");
        try{
            Optional<Supplier> OptionalSupplier = supplierRepository.findById(supplierId);
            if(!OptionalSupplier.isPresent()){
                throw new RuntimeException("Supplier Not Found");
            }
            Supplier supplier = OptionalSupplier.get();
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setSupplierId(supplier.getSupplierId());
            supplierDTO.setSupplierName(supplier.getSupplierName());
            supplierDTO.setSupplierContactEmail(supplier.getSupplierContactEmail());
            supplierDTO.setSupplierAddress(supplier.getSupplierAddress());
            supplierDTO.setSupplierPhoneNumber(supplier.getSupplierPhoneNumber());
            return supplierDTO;

        }catch(Exception e){
            log.error("Error in getSupplierById method: " + e.getMessage());
            throw e;
        }

    }
}
