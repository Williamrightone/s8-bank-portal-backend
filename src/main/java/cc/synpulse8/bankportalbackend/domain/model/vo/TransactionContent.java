package cc.synpulse8.bankportalbackend.domain.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionContent {

    private Long id;

    private String uniqueIdentifier;

    private String transactionType;

    private String currencyCode;

    private BigDecimal amount;

    private String accountIBAN;

    private LocalDate valueDate;

    private String description;

}
