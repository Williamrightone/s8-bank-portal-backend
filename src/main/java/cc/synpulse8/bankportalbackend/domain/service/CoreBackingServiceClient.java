package cc.synpulse8.bankportalbackend.domain.service;

import cc.synpulse8.bankportalbackend.domain.model.vo.TransactionContent;
import cc.synpulse8.bankportalbackend.presentation.dto.request.TransactionViewRequest;
import cc.synpulse8.bankportalbackend.presentation.dto.response.TransactionViewResponse;
import cc.synpulse8.bankportalbackend.util.rest.RestfulResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CoreBackingServiceClient {

    TransactionViewResponse getTransactionListBetween(TransactionViewRequest transactionViewRequest);

}
