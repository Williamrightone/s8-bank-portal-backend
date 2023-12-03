package cc.synpulse8.bankportalbackend.presentation.dto.response;

import cc.synpulse8.bankportalbackend.domain.model.vo.TransactionContent;
import lombok.Data;

import java.util.List;

@Data
public class TransactionViewResponse {

    List<TransactionContent> transactions;

    int totalPages;

    long totalElements;

}
