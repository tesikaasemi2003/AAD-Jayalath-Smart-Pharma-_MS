package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.DrugDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Category;
import lk.ijse.Jayalath_Smart_Pharma.entity.Drug;
import lk.ijse.Jayalath_Smart_Pharma.repository.CategoryRepository;
import lk.ijse.Jayalath_Smart_Pharma.repository.DrugRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.DrugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DrugServiceImpl implements DrugService {
    private final DrugRepository drugRepository;
    private final CategoryRepository categoryRepository;

    public DrugServiceImpl(DrugRepository drugRepository, CategoryRepository categoryRepository) {
        this.drugRepository = drugRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void saveDrug(DrugDTO drugDTO) {
        log.info("Executing saveDrug method");
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(drugDTO.getCategoryId());
            if (!optionalCategory.isPresent()) {
                throw new RuntimeException("Category Not Found");
            }
            Drug drug = new Drug();
            drugDTO.setBrandName(drugDTO.getBrandName());
            drugDTO.setGenericName(drugDTO.getGenericName());
            drugDTO.setReorderLevel(drugDTO.getReorderLevel());
            drugDTO.setUnit(drugDTO.getUnit());
            drugDTO.setCategory(optionalCategory.get());

            drugRepository.save(drug);

        } catch (Exception e) {
            log.error("Error in saveDrug method: " + e.getMessage());
            throw e;
        }
    }
    @Override
    public List<DrugDTO> getAllDrugs() {
        log.info("Executing getAllDrugs method");
        try{
            List<Drug> drugs = drugRepository.findAll();
            List<DrugDTO> drugDTOs = new ArrayList<>();
            for (Drug drug : drugs) {
                DrugDTO drugDTO = new DrugDTO();
                drugDTO.setDrugId(drug.getDrugId());
                drugDTO.setBrandName(drug.getBrandName());
                drugDTO.setGenericName(drug.getGenericName());
                drugDTO.setReorderLevel(drug.getReorderLevel());
                drugDTO.setUnit(drug.getUnit());
                if (drug.getCategory() != null) {
                    drugDTO.setCategoryId(drug.getCategory().getCategoryId());
                }
                drugDTOs.add(drugDTO);
            }
            return drugDTOs;
        } catch (Exception e) {
            log.error("Error in getAllDrugs method: " + e.getMessage());
            throw e;
        }
    }

    public DrugDTO getDrugById(Long drugId) {
        log.info("Executing getDrugById method");
        try {
            Optional<Drug> optionalDrug = drugRepository.findById(drugId);
            if (!optionalDrug.isPresent()) {
                throw new RuntimeException("User not found in this Id");
            }
            Drug drug = optionalDrug.get();
            DrugDTO drugDTO = new DrugDTO();
            drugDTO.setDrugId(drug.getDrugId());
            drugDTO.setBrandName(drug.getBrandName());
            drugDTO.setGenericName(drug.getGenericName());
            drugDTO.setReorderLevel(drug.getReorderLevel());
            drugDTO.setUnit(drug.getUnit());
            if (drug.getCategory() != null) {
                drugDTO.setCategoryId(drug.getCategory().getCategoryId());
            }
            return drugDTO;
        } catch (Exception e) {
            log.error("Error in getDrugById method: " + e.getMessage());
            throw e;
        }
    }
    @Override
    @Transactional
    public void updateDrug(Long drugId, DrugDTO drugDTO) {
        log.info("Executing updateDrug method");
        try {
            Optional<Drug> optionalDrug = drugRepository.findById(drugId);
            if (!optionalDrug.isPresent()) {
                throw new RuntimeException("Drug is Not Found");
            }
            Optional<Category> optionalCategory = categoryRepository.findById(drugDTO.getCategoryId());
            if (!optionalCategory.isPresent()) {
                throw new RuntimeException("Category Not Found");
            }
            Drug drug = optionalDrug.get();
            drugDTO.setBrandName(drugDTO.getBrandName());
            drugDTO.setGenericName(drugDTO.getGenericName());
            drugDTO.setReorderLevel(drugDTO.getReorderLevel());
            drugDTO.setUnit(drugDTO.getUnit());
            drugDTO.setCategory(optionalCategory.get());
            drugRepository.save(drug);
        } catch (Exception e) {
            log.error("Error in updateDrug method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteDrug(Long drugId) {
        log.info("Executing deleteDrug method");
        try {
            Optional<Drug> optionalDrug = drugRepository.findById(drugId);
            if (!optionalDrug.isPresent()) {
                throw new RuntimeException("Drug is Not Found");
            }
            drugRepository.deleteById(drugId);
        } catch (Exception e) {
            log.error("Error in deleteDrug method: " + e.getMessage());
            throw e;
        }
    }
    }

