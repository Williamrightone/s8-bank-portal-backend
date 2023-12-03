package cc.synpulse8.bankportalbackend.domain.service;

import cc.synpulse8.bankportalbackend.presentation.dto.request.TransactionViewRequest;
import cc.synpulse8.bankportalbackend.presentation.dto.response.TransactionViewResponse;

public interface CoreBackingServiceClient {

    TransactionViewResponse getTransactionListBetween(TransactionViewRequest transactionViewRequest);

}
