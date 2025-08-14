package com.example.be.web.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestData<T> {

    private RestStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public RestData(T data) {
        this.data= data;
        this.status = RestStatus.SUCCESS;
    }

    public static RestData<?> error(Object message ){
        return new RestData<>(RestStatus.ERROR, message,null);
    }
}
