package cc.synpulse8.bankportalbackend.domain.service.impl;

import cc.synpulse8.bankportalbackend.domain.service.CoreBackingServiceClient;
import cc.synpulse8.bankportalbackend.infrastructure.handler.GlobalServiceException;
import cc.synpulse8.bankportalbackend.presentation.dto.request.TransactionViewRequest;
import cc.synpulse8.bankportalbackend.presentation.dto.response.TransactionViewResponse;
import cc.synpulse8.bankportalbackend.util.rest.RestfulResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class CoreBackingServiceClientImpl implements CoreBackingServiceClient {

    @Value("${client.core-banking-services.url}")
    String coreBankingServiceUrl;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public TransactionViewResponse getTransactionListBetween(TransactionViewRequest transactionViewRequest) {

        String getTransactionListBetweenURL = coreBankingServiceUrl + "/api/core-banking/transactions";

        ResponseEntity<String> response = restTemplate.postForEntity(getTransactionListBetweenURL, transactionViewRequest, String.class);

        if(!response.getStatusCode().equals(HttpStatus.OK)) {
            log.info("Core Banking Services 處理失敗: " + response.getBody());
        }

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.registerModule(new JavaTimeModule());

        TransactionViewResponse transactionViewResponse = null;

        try {

            RestfulResponse restfulResponse = objMapper.readValue(response.getBody(), RestfulResponse.class);
            log.info("Core Banking Services 回應: " + restfulResponse.getData());
            String json = objMapper.writeValueAsString(restfulResponse.getData());

            transactionViewResponse =  objMapper.readValue(json, TransactionViewResponse.class);


        } catch (JsonProcessingException e) {
            log.info("JsonProcessingException");
            throw new GlobalServiceException(GlobalServiceException.GlobalServiceErrorType.REQUEST_JSON_PARSE_ERROR, "Core Banking Service Request 解析錯誤");
        }

        return transactionViewResponse;
    }
}
