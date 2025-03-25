package dataAccess.repositories;

import jakarta.transaction.Transactional;
import models.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    List<Category> findAllByNameIn(List<String> categoriesNames);
    List<Category> findAllByMccCodesIn(List<Integer> mccCodes);
    @Transactional
    Category save(Category category);

    @Query("SELECT c.name FROM Category c")
    List<String> findAllCategoriesNames();
    Category deleteByName(String categoryName);
}
