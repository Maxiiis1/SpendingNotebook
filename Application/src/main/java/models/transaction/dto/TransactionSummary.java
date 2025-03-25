package models.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class TransactionSummary {
    private String category;
    private BigDecimal totalAmount;
    private BigDecimal percentage;
}

