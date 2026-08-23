package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.dto.CategoryDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Category;
import lk.ijse.Jayalath_Smart_Pharma.repository.CategoryRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    @Transactional
    public void saveCategory(CategoryDTO categoryDTO) {
        log.info("Executing saveCategory method");
        try{
            Category category = new Category();
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setCategoryDescription(categoryDTO.getCategoryDescription());
            categoryRepository.save(category);
        }catch(Exception e){
            log.error("Error in saveCategory method" +e.getMessage());

        }
    }
    @Override
    public List<CategoryDTO> getAllCategories() {
        log.info("Executing getAllCategories method");
        try{
            List<Category> categories = categoryRepository.findAll();
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            for (Category category : categories) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setCategoryId(category.getCategoryId());
                categoryDTO.setCategoryName(category.getCategoryName());
                categoryDTO.setCategoryDescription(category.getCategoryDescription());
            }
            return categoryDTOS;
        }catch(Exception e){
            log.error("Error in getAllCategories method" +e.getMessage());
            throw e;
        }
    }
}
