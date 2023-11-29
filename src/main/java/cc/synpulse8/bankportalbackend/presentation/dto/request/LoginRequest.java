package cc.synpulse8.bankportalbackend.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

@Data
public class LoginRequest {

    @NotBlank(message = "sid can not be null")
    private String sid;

    @NotBlank(message = "password can not be null")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "password format error")
    private String password;

}
