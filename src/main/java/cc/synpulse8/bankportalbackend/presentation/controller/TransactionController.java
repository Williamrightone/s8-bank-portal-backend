package cc.synpulse8.bankportalbackend.presentation.controller;

import cc.synpulse8.bankportalbackend.application.TransactionInfoService;
import cc.synpulse8.bankportalbackend.presentation.dto.request.TransactionViewRequest;
import cc.synpulse8.bankportalbackend.presentation.dto.response.TransactionViewResponse;
import cc.synpulse8.bankportalbackend.util.rest.RestfulResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionInfoService transactionService;

    public TransactionController(TransactionInfoService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    @PreAuthorize("hasAuthority('ACCOUNTING_VIEW')")
    public RestfulResponse<TransactionViewResponse> viewTransactionContentPage(@RequestBody TransactionViewRequest transactionViewRequest) {

        return transactionService.getTransactionListBetween(transactionViewRequest);
    }

}
