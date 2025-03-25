package businessLogic.transactions;

import contracts.transactions.ITransactionService;
import dataAccess.repositories.CategoryJpaRepository;
import dataAccess.repositories.MccJpaRepository;
import dataAccess.repositories.TransactionJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import models.categories.Category;
import models.categories.Mcc;
import models.transaction.Transaction;
import models.transaction.dto.TransactionDTO;
import models.transaction.dto.TransactionSummary;
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
    private final MccJpaRepository mccRepository;
    private final CategoryJpaRepository categoryRepository;

    @Override
    public Transaction addTransaction(TransactionDTO transactionDto) throws EntityNotFoundException{

        Category category = categoryRepository.findByName(transactionDto.getCategoryName());
        if (category == null) {
            throw new EntityNotFoundException("Category with name " + transactionDto.getCategoryName() + " not found");
        }

        Transaction transaction = Transaction.builder()
                .name(transactionDto.getName())
                .value(transactionDto.getValue())
                .month(transactionDto.getMonth())
                .category(category)
                .build();

        if (transactionDto.getMccCode() != null) {
            Mcc mcc = mccRepository.findByMccCode(transactionDto.getMccCode());
            if (mcc == null) {
                throw new EntityNotFoundException("MCC with code " + transactionDto.getMccCode() + " not found");
            }
            transaction.setMcc(mcc);
        }

        return transactionRepository.save(transaction);
    }


    @Override
    public List<TransactionSummary> showTransactionsByMonthInCategories(String month) {
        List<TransactionSummary> summaries = new ArrayList<>();

        List<Transaction> transactions = transactionRepository.findAllByMonth(month);

        Map<Category, BigDecimal> categoryAmounts = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCategory,
                        Collectors.mapping(Transaction::getValue,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        BigDecimal totalAmount = transactions.stream()
                .map(Transaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        for (Map.Entry<Category, BigDecimal> data : categoryAmounts.entrySet()) {
            Category category = data.getKey();
            BigDecimal categoryAmount = data.getValue();
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
        List<Transaction> transactions = transactionRepository.findAllByCategory_Name(categoryName);

        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getMonth,
                        Collectors.mapping(Transaction::getValue,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    @Override
    public void removeTransaction(String name, String month, BigDecimal value) throws EntityNotFoundException {
        Transaction transaction = transactionRepository.findByNameAndMonthAndValue(name, month, value);
        if (transaction == null){
            throw new EntityNotFoundException("Given transaction" + name + " " + month + " " + value + " was not found!");
        }

        transactionRepository.delete(transaction);
    }
}
