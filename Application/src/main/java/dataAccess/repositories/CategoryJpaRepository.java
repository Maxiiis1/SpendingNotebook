package dataAccess.repositories;

import jakarta.transaction.Transactional;
import models.categories.Category;
import models.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("categoryRepository")
public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
    Category findCategoryByName(String name);
    @Transactional
    Category save(Category category);

    @Override
    List<Category> findAll();
}
