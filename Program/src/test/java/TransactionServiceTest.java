import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.*;

import businessLogic.transactions.TransactionService;
import dataAccess.repositories.TransactionJpaRepository;
import models.categories.Category;
import models.transaction.Transaction;
import models.transaction.dto.TransactionSummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionJpaRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testShowTransactionsByMonthInCategories_WhenTwoCategories() {
        String month = "Декабрь";
        Category category1 = Category.builder().name("Еда").build();
        Category category2 = Category.builder().name("Развлечения").build();

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(Transaction.builder().category(category1).value(BigDecimal.valueOf(300)).month("Декабрь").build());
        transactions.add(Transaction.builder().category(category1).value(BigDecimal.valueOf(320)).month("Декабрь").build());
        transactions.add(Transaction.builder().category(category2).value(BigDecimal.valueOf(470)).month("Декабрь").build());
        transactions.add(Transaction.builder().category(category2).value(BigDecimal.valueOf(500)).month("Декабрь").build());

        when(transactionRepository.findAllByMonth(month)).thenReturn(transactions);

        List<TransactionSummary> summaries = transactionService.showTransactionsByMonthInCategories(month);

        assertEquals(2, summaries.size());

        for (TransactionSummary summary : summaries) {
            if (summary.getCategory().equals("Еда")) {
                assertEquals(BigDecimal.valueOf(620), summary.getTotalAmount());
                assertEquals(BigDecimal.valueOf(38.99), summary.getPercentage());
            }
            else if (summary.getCategory().equals("Развлечения")) {
                assertEquals(BigDecimal.valueOf(970), summary.getTotalAmount());
                assertEquals(BigDecimal.valueOf(61.01), summary.getPercentage());
            }
        }
    }

    @Test
    public void testShowTransactionsByCategoryPerMonths() {
        String categoryName = "Еда";
        Transaction transaction1 = Transaction.builder().month("Январь").value(new BigDecimal("700")).build();
        Transaction transaction2 = Transaction.builder().month("Январь").value(new BigDecimal("300")).build();
        Transaction transaction3 = Transaction.builder().month("Февраль").value(new BigDecimal("800")).build();
        Transaction transaction4 = Transaction.builder().month("Февраль").value(new BigDecimal("300")).build();
        Transaction transaction5 = Transaction.builder().month("Март").value(new BigDecimal("900")).build();

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3, transaction4, transaction5);

        when(transactionRepository.findAllByCategory_Name(categoryName)).thenReturn(transactions);

        Map<String, BigDecimal> result = transactionService.showTransactionsByCategoryPerMonths(categoryName);

        Map<String, BigDecimal> expected = new HashMap<>();
        expected.put("Январь", new BigDecimal("1000"));
        expected.put("Февраль", new BigDecimal("1100"));
        expected.put("Март", new BigDecimal("900"));
        assertEquals(expected, result);
    }
}
