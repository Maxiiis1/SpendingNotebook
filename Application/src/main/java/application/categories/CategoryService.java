package application.categories;

import contracts.categories.ICategoryService;
import dataAccess.repositories.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import models.categories.Category;
import models.dto.CategoryRequestBody;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryServiceBean")
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryJpaRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> showCategories() {
        return null;
    }

    @Override
    public Category addMccCodesToCategory(String categoryName, List<Integer> mccCodes) {
        return null;
    }

    @Override
    public Category addCategoriesToCategory(String categoryName, List<String> categoriesToAdd) {
        return null;
    }

    @Override
    public void removeCategoryByName(String categoryName) {

    }
}
