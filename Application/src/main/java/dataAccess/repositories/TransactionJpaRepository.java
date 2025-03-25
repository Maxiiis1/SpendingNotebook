package dataAccess.repositories;

import jakarta.transaction.Transactional;
import models.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {
    @Transactional
    Transaction save(Transaction transaction);
    List<Transaction> findAllByCategory_Name(String categoryName);

    List<Transaction> findAllByMonth(String month);
    Transaction findByNameAndMonthAndValue(String name, String month, BigDecimal value);
    void delete(Transaction transaction);
}
