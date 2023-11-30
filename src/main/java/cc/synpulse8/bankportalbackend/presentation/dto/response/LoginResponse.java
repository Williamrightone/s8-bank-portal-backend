package cc.synpulse8.bankportalbackend.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginResponse {

    private String sid;

    private String userName;

    private String accessToken;

    private String refreshToken;

}
