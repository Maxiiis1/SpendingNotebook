package contracts.transactions;

import models.transaction.Transaction;
import models.dto.TransactionSummary;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ITransactionService {
    Transaction addTransaction(Transaction transaction);
    List<TransactionSummary> showTransactionsByMonth(String month);
    Map<String, BigDecimal> showTransactionsByCategoryPerMonths(String categoryName);
    void removeTransaction(String transactionName);
}
