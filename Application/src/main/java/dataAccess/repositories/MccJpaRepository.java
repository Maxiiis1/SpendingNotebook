package dataAccess.repositories;

import models.categories.Mcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MccJpaRepository extends JpaRepository<Mcc, Long> {
    Mcc findByMccCode(Integer mmc);
}
