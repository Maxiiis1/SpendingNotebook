package businessLogic.categories;

import contracts.categories.ICategoryService;
import dataAccess.repositories.CategoryJpaRepository;
import dataAccess.repositories.MccJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import models.categories.Category;
import models.categories.Mcc;
import models.categories.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("categoryServiceBean")
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryJpaRepository categoryRepository;
    private final MccJpaRepository mccRepository;

    @Override
    public List<String> addCategory(CategoryDTO categoryDto) throws IllegalArgumentException{
        if (categoryRepository.findByName(categoryDto.getName()) != null){
            throw new IllegalArgumentException("Given category already exists: " + categoryDto.getName());
        }

        List<Category> categoriesWithSameMcc = categoryRepository.findAllByMccCodesIn(categoryDto.getMccCodes());
        if (!categoriesWithSameMcc.isEmpty()) {
            throw new IllegalArgumentException("MCC already reserved for category " + categoriesWithSameMcc.get(0).getName());
        }

        List<Mcc> mccCodes = categoryDto.getMccCodes().stream()
                .map(mccCode -> Mcc.builder().mccCode(mccCode).build())
                .collect(Collectors.toList());

        Category category = Category.builder()
                .name(categoryDto.getName())
                .mccCodes(mccCodes)
                .subCategories(categoryRepository.findAllByNameIn(categoryDto.getSubCategories()))
                .build();
        categoryRepository.save(category);

        return categoryRepository.findAllCategoriesNames();
    }

    @Override
    public List<String> showCategoriesNames() {
        return categoryRepository.findAllCategoriesNames();
    }

    @Override
    public List<Integer> addMccCodesToCategory(String categoryName, List<Integer> mccCodes) throws IllegalArgumentException{
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            throw new IllegalArgumentException("Category was not found: " + categoryName);
        }

        List<Category> categoriesWithSameMcc = categoryRepository.findAllByMccCodesIn(mccCodes);
        if (!categoriesWithSameMcc.isEmpty()) {
            throw new IllegalArgumentException("MCC already reserved for category " + categoriesWithSameMcc.get(0).getName());
        }

        List<Mcc> mccList = mccCodes.stream()
                .map(mccCode -> {
                    Mcc mcc = mccRepository.findByMccCode(mccCode);
                    return mcc != null ? mcc : Mcc.builder().mccCode(mccCode).build();
                })
                .collect(Collectors.toList());
        category.getMccCodes().addAll(mccList);
        categoryRepository.save(category);

        return category.getMccCodes().stream()
                .map(Mcc::getMccCode)
                .collect(Collectors.toList());
    }


    @Override
    public List<String> addCategoriesToCategory(String categoryName, List<String> categoriesToAdd) throws EntityNotFoundException{
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            throw new EntityNotFoundException("Category was not found: " + categoryName);
        }

        List<Category> categoriesToAddList = categoriesToAdd.stream()
                .map(categoryRepository::findByName)
                .peek(categoryToAdd -> {
                    if (categoryToAdd == null) {
                        throw new EntityNotFoundException("Not all categories you want to add exists!");
                    }
                })
                .collect(Collectors.toList());

        category.getSubCategories().addAll(categoriesToAddList);
        categoryRepository.save(category);

        return category.getSubCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }


    @Override
    public List<String> removeCategoryByName(String categoryName) throws EntityNotFoundException {
        if (categoryRepository.deleteByName(categoryName) == null){
            throw new EntityNotFoundException("Category was not found: " + categoryName);
        }

        return categoryRepository.findAllCategoriesNames();
    }
}
