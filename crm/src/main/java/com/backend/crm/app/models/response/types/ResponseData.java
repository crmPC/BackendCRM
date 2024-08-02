package com.backend.crm.app.models.response.types;

import com.backend.crm.app.models.response.types.Response;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonPropertyOrder({"status", "message", "data"})
public class ResponseData<T> extends Response {
    private final T data;

    public ResponseData(int status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}
