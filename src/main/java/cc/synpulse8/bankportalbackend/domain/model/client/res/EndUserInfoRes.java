package cc.synpulse8.bankportalbackend.domain.model.client.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndUserInfoRes implements Serializable {

    private String sid;

    private String passwd;

    private String userName;

}
