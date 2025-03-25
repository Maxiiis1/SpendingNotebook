package restControllers;

import contracts.categories.ICategoryService;
import lombok.RequiredArgsConstructor;
import models.categories.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoriesController {
    private final ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> addCategory(@RequestBody CategoryDTO dto) {
        List<String> updatedCategoryList = categoryService.addCategory(dto);
        String message = "Created new category: " + dto.getName() + " in category list: " + updatedCategoryList;
        return ResponseEntity.ok(message);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> showCategoriesNames() {
        List<String> categoryNames = categoryService.showCategoriesNames();
        return ResponseEntity.ok(categoryNames);
    }

    @PostMapping("/{categoryName}/mcc-codes")
    public ResponseEntity<String> addMccCodesToCategory(@PathVariable("categoryName") String categoryName, @RequestBody List<Integer> mccCodes) {
        List<Integer> updatedMccList = categoryService.addMccCodesToCategory(categoryName, mccCodes);
        String message = "Added new mcc codes to " + categoryName + " in mcc list of this category: " + updatedMccList;
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{categoryName}/subcategories")
    public ResponseEntity<String> addCategoriesToCategory(@PathVariable("categoryName") String categoryName, @RequestBody List<String> categoriesToAdd) {
        List<String> updatedCategoryList = categoryService.addCategoriesToCategory(categoryName, categoriesToAdd);
        String message = "Added new categories to" + categoryName + " in subcategory list: " + updatedCategoryList;
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{categoryName}")
    public ResponseEntity<String> removeCategoryByName(@PathVariable("categoryName") String categoryName) {
        List<String> categoriesNames = categoryService.removeCategoryByName(categoryName);
        String message = "Category " + categoryName + " was deleted from " + categoriesNames;
        return ResponseEntity.ok(message);
    }
}
