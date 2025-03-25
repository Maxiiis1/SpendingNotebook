package contracts.categories;

import models.categories.Category;
import models.categories.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    List<String> addCategory(CategoryDTO categoryDto);
    List<String> showCategoriesNames();
    List<Integer> addMccCodesToCategory(String categoryName, List<Integer> mccCodes);
    List<String> addCategoriesToCategory(String categoryName, List<String> categoriesToAdd);
    List<String> removeCategoryByName(String categoryName);
}
