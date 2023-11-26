package cc.synpulse8.bankportalbackend.application;

import cc.synpulse8.bankportalbackend.presentation.dto.request.LoginRequest;
import cc.synpulse8.bankportalbackend.presentation.dto.response.LoginResponse;
import cc.synpulse8.bankportalbackend.util.rest.RestfulResponse;

public interface UserOnboardService {

    public RestfulResponse<LoginResponse> login(LoginRequest loginUserRequest);

}
