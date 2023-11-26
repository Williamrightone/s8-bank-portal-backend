package cc.synpulse8.bankportalbackend.util.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class RestfulResponse <T> implements Serializable {

    private T data;

    public RestfulResponse(T data) {
        this.data = data;
    }

}
