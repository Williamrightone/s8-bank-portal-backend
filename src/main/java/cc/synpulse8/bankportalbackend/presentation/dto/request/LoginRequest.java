package cc.synpulse8.bankportalbackend.presentation.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String internalId;

    private String password;

}
