package application.transactions;

import contracts.transactions.ITransactionService;
import dataAccess.repositories.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import models.categories.Category;
import models.transaction.Transaction;
import models.dto.TransactionSummary;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("transactionServiceBean")
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {
    private final TransactionJpaRepository transactionRepository;

    @Override
    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionSummary> showTransactionsByMonth(String month) {
        List<TransactionSummary> summaries = new ArrayList<>();

        // Получить список всех транзакций за выбранный месяц
        List<Transaction> transactions = transactionRepository.findTransactionsByMonth(month);

        // Сгруппировать транзакции по категориям и вычислить суммы для каждой категории
        Map<Category, BigDecimal> categoryAmounts = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCategory,
                        Collectors.mapping(Transaction::getValue,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        // Вычислить общую сумму трат за месяц
        BigDecimal totalAmount = transactions.stream()
                .map(Transaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Создать объекты TransactionSummary для каждой категории
        for (Map.Entry<Category, BigDecimal> entry : categoryAmounts.entrySet()) {
            Category category = entry.getKey();
            BigDecimal categoryAmount = entry.getValue();
            BigDecimal categoryPercentage = categoryAmount.multiply(BigDecimal.valueOf(100))
                    .divide(totalAmount, 2, BigDecimal.ROUND_HALF_UP)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);

            TransactionSummary summary = TransactionSummary.builder()
                    .category(category.getName())
                    .totalAmount(categoryAmount)
                    .percentage(categoryPercentage).build();
            summaries.add(summary);
        }

        return summaries;
    }


    @Override
    public Map<String, BigDecimal> showTransactionsByCategoryPerMonths(String categoryName) {
        return null;
    }

    @Override
    public void removeTransaction(String transactionName) {

    }
}
