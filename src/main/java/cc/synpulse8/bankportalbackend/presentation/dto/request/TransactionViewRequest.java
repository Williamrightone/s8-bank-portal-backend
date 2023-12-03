package cc.synpulse8.bankportalbackend.presentation.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionViewRequest {

    private int page;

    private int size;

    private LocalDate startDate;

    private LocalDate endDate;

    private String iban;

}
