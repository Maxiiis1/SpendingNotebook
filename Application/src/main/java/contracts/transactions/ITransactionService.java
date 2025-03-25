package contracts.transactions;

import models.transaction.Transaction;
import models.transaction.dto.TransactionDTO;
import models.transaction.dto.TransactionSummary;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ITransactionService {
    Transaction addTransaction(TransactionDTO transactionDto);
    List<TransactionSummary> showTransactionsByMonthInCategories(String month);
    Map<String, BigDecimal> showTransactionsByCategoryPerMonths(String categoryName);
    void removeTransaction(String name, String month, BigDecimal value);
}
