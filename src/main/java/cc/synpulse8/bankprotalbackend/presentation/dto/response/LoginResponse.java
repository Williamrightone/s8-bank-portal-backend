package cc.synpulse8.bankprotalbackend.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginResponse {

    private String internalId;

    private String userName;

    private String accessToken;

    private String refreshToken;

}
