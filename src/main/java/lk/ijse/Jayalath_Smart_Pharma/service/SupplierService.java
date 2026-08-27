package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {
    public void saveSupplier(SupplierDTO supplierDTO);
    public List<SupplierDTO> getAllSuppliers();
    public SupplierDTO getSupplierById(Long supplierId);
    public void updateSupplier(Long supplierId, SupplierDTO supplierDTO);
    public void deleteSupplier(Long supplierId);
}
