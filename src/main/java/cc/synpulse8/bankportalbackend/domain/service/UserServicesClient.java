package cc.synpulse8.bankportalbackend.domain.service;

import cc.synpulse8.bankportalbackend.domain.model.client.res.EndUserInfoRes;

import java.util.List;

public interface UserServicesClient {

    public EndUserInfoRes getUserInfoBySid(String sid);

    public List<String> getPermissionsBySid(String sid);

}
