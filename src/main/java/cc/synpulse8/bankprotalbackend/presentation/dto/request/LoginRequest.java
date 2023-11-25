package cc.synpulse8.bankprotalbackend.presentation.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String internalId;

    private String password;

}
