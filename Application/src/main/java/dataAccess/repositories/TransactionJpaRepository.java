package dataAccess.repositories;

import jakarta.transaction.Transactional;
import models.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository("transactionRepository")
public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {
    @Transactional
    Transaction save(Transaction transaction);
    List<Transaction> findTransactionsByCategory_NameAndAndMonth(String categoryName, String month);

    List<Transaction> findTransactionsByCategory_Name(String categoryName);

    List<Transaction> findTransactionsByMonth(String month);
    void deleteFirstByNameAndMonthAndValue(String name, String month, BigDecimal value);
}
