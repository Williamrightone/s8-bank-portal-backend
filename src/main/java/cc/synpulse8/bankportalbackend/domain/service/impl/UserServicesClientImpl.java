package cc.synpulse8.bankportalbackend.domain.service.impl;

import cc.synpulse8.bankportalbackend.domain.error.UserOnBoardException;
import cc.synpulse8.bankportalbackend.domain.model.client.res.EndUserInfoRes;
import cc.synpulse8.bankportalbackend.domain.service.UserServicesClient;
import cc.synpulse8.bankportalbackend.infrastructure.handler.GlobalServiceException;
import cc.synpulse8.bankportalbackend.util.rest.RestfulResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServicesClientImpl implements UserServicesClient {

    @Value("${client.user-services.url}")
    String userServiceUrl;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public EndUserInfoRes getUserInfoBySid(String sid) {

        String getUserInfoURL =  userServiceUrl + "/api/end-user/" + sid;

        ResponseEntity<String> response = restTemplate.getForEntity(getUserInfoURL, String.class);

        if(!response.getStatusCode().equals(HttpStatus.OK)) {
            log.info("User Services 處理失敗: " + response.getBody());
            throw new UserOnBoardException(UserOnBoardException.UserServiceErrorType.USER_NOT_FOUND, "User Not Found");
        }

        log.info("Response from User Service: "+response.getBody());
        ObjectMapper objMapper = new ObjectMapper();

        EndUserInfoRes endUserInfoRes = new EndUserInfoRes();

        try {

            RestfulResponse restfulResponse = objMapper.readValue(response.getBody(), RestfulResponse.class);

            String json = objMapper.writeValueAsString(restfulResponse.getData());

            endUserInfoRes = objMapper.readValue(json, EndUserInfoRes.class);

        } catch (JsonProcessingException e) {
            log.info("JsonProcessingException");
            throw new GlobalServiceException(GlobalServiceException.GlobalServiceErrorType.REQUEST_JSON_PARSE_ERROR, "User Service Request 解析錯誤");
        }

        return endUserInfoRes;
    }

    @Override
    public List<String> getPermissionsBySid(String sid) {

        String getPermissionListURL = userServiceUrl + "/api/permission/" + sid;

        ResponseEntity<String> response = restTemplate.getForEntity(getPermissionListURL, String.class);

        log.info("Response from User Service: "+response.getBody());

        if(!response.getStatusCode().equals(HttpStatus.OK)) {
            log.info("User Services 處理失敗: " + response.getBody());
            //TODO 完善架構
        }

        ObjectMapper objMapper = new ObjectMapper();

        List<String> permissions = new ArrayList<>();

        try {

            RestfulResponse restfulResponse = objMapper.readValue(response.getBody(), RestfulResponse.class);

            String json = objMapper.writeValueAsString(restfulResponse.getData());

            permissions = objMapper.readValue(json, List.class);

        } catch (JsonProcessingException e) {
            log.info("JsonProcessingException");
            throw new GlobalServiceException(GlobalServiceException.GlobalServiceErrorType.REQUEST_JSON_PARSE_ERROR, "User Service Request 解析錯誤");
        }

        return permissions;
    }


}
