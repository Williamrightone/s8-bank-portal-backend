package cc.synpulse8.bankprotalbackend.application;

import cc.synpulse8.bankprotalbackend.presentation.dto.request.LoginRequest;
import cc.synpulse8.bankprotalbackend.presentation.dto.response.LoginResponse;
import cc.synpulse8.bankprotalbackend.util.rest.RestfulResponse;

public interface UserOnboardService {

    public RestfulResponse<LoginResponse> login(LoginRequest loginUserRequest);

}
