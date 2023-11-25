package cc.synpulse8.bankprotalbackend.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndUserEntity implements Serializable {

    private String internalId;

    private String userName;

    private String password;

}
