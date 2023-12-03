package cc.synpulse8.bankportalbackend.application;

import cc.synpulse8.bankportalbackend.domain.model.vo.TransactionContent;
import cc.synpulse8.bankportalbackend.presentation.dto.request.TransactionViewRequest;
import cc.synpulse8.bankportalbackend.presentation.dto.response.TransactionViewResponse;
import cc.synpulse8.bankportalbackend.util.rest.RestfulResponse;
import org.springframework.data.domain.Page;

public interface TransactionInfoService {

    RestfulResponse<TransactionViewResponse> getTransactionListBetween(TransactionViewRequest transactionViewRequest);

}
