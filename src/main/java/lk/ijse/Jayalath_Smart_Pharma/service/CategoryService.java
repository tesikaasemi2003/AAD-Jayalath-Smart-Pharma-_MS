package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    public void saveCategory(CategoryDTO categoryDTO);
    public List<CategoryDTO> getAllCategories();
}
