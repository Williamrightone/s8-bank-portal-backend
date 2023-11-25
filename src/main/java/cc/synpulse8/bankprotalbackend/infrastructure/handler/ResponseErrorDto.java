package cc.synpulse8.bankprotalbackend.infrastructure.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorDto {

    private HttpStatus httpStatus;

    private String customErrorCode;

}
