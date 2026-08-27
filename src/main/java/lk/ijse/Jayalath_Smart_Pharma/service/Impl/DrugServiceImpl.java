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
                DrugDTO dto = new DrugDTO();
                dto.setDrugId(drug.getDrugId());
                dto.setBrandName(drug.getBrandName());
                dto.setGenericName(drug.getGenericName());
                dto.setReorderLevel(drug.getReorderLevel());
                dto.setUnit(drug.getUnit());
                if (drug.getCategory() != null) {
                    dto.setCategoryId(drug.getCategory().getCategoryId());
                }
                drugDTOs.add(dto);
            }
            return drugDTOs;
        } catch (Exception e) {
            log.error("Error in getAllDrugs method: " + e.getMessage());
            throw e;
        }
    }

    }

