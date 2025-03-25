package restControllers;

import contracts.transactions.ITransactionService;
import lombok.RequiredArgsConstructor;
import models.transaction.Transaction;
import models.transaction.dto.TransactionDTO;
import models.transaction.dto.TransactionSummary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final ITransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(@RequestBody TransactionDTO transactionDto) {
        Transaction addedTransaction = transactionService.addTransaction(transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTransaction);
    }

    @GetMapping("/by-month/{month}")
    public ResponseEntity<List<TransactionSummary>> showTransactionsByMonth(@PathVariable("month") String month) {
        List<TransactionSummary> transactionSummaries = transactionService.showTransactionsByMonthInCategories(month);
        return ResponseEntity.ok(transactionSummaries);
    }

    @GetMapping("/by-category/{categoryName}")
    public ResponseEntity<Map<String, BigDecimal>> showTransactionsByCategoryPerMonths(@PathVariable("categoryName") String categoryName) {
        Map<String, BigDecimal> transactionSummaryByCategory = transactionService.showTransactionsByCategoryPerMonths(categoryName);
        return ResponseEntity.ok(transactionSummaryByCategory);
    }

    @DeleteMapping
    public ResponseEntity<String> removeTransaction(
            @RequestParam("name") String name,
            @RequestParam("month") String month,
            @RequestParam("value") BigDecimal value) {

        transactionService.removeTransaction(name, month, value);
        String message = "Transaction " + name + " was deleted";
        return ResponseEntity.ok(message);
    }
}
