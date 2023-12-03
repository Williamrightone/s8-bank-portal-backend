package cc.synpulse8.bankportalbackend.application.impl;

import cc.synpulse8.bankportalbackend.application.TransactionInfoService;
import cc.synpulse8.bankportalbackend.domain.model.vo.TransactionContent;
import cc.synpulse8.bankportalbackend.domain.service.CoreBackingServiceClient;
import cc.synpulse8.bankportalbackend.presentation.dto.request.TransactionViewRequest;
import cc.synpulse8.bankportalbackend.presentation.dto.response.TransactionViewResponse;
import cc.synpulse8.bankportalbackend.util.rest.RestfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TransactionInfoServiceImpl implements TransactionInfoService {

    @Autowired
    CoreBackingServiceClient coreBackingServiceClient;

    @Override
    public RestfulResponse<TransactionViewResponse> getTransactionListBetween(TransactionViewRequest transactionViewRequest) {
        return new RestfulResponse<TransactionViewResponse>(coreBackingServiceClient.getTransactionListBetween(transactionViewRequest));
    }
}
