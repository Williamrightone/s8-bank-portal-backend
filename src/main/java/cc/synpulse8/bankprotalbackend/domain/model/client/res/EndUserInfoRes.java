package cc.synpulse8.bankprotalbackend.domain.model.client.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndUserInfoRes implements Serializable {

    private String internalId;

    private String password;

    private String userName;

}
