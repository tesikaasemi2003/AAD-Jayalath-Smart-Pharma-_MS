package lk.ijse.Jayalath_Smart_Pharma.controller;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.CategoryDTO;
import lk.ijse.Jayalath_Smart_Pharma.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseCode.OPERATION_SUCCESS;
import static lk.ijse.Jayalath_Smart_Pharma.constant.ResponseMessage.SUCCESS_MESSAGE;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CommonResponse saveCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.saveCategory(categoryDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
    @GetMapping
    public CommonResponse getAllCategories() {
        List<CategoryDTO> categoryList = categoryService.getAllCategories();
        return new CommonResponse(OPERATION_SUCCESS ,SUCCESS_MESSAGE,categoryList);
    }
    @GetMapping("/{categoryId}")
    public CommonResponse getCategoryById(@PathVariable Long categoryId) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
        return new CommonResponse(OPERATION_SUCCESS ,SUCCESS_MESSAGE,categoryDTO);
    }
}
