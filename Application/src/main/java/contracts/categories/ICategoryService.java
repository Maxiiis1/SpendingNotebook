package contracts.categories;

import models.categories.Category;
import models.dto.CategoryRequestBody;

import java.util.List;

public interface ICategoryService {
    Category addCategory(Category category);
    List<Category> showCategories();
    Category addMccCodesToCategory(String categoryName, List<Integer> mccCodes);
    Category addCategoriesToCategory(String categoryName, List<String> categoriesToAdd);
    void removeCategoryByName(String categoryName);
}
