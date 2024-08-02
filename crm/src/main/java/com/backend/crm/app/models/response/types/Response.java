package com.backend.crm.app.models.response.types;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonPropertyOrder({"status", "message"})
public class Response {
    private final HttpStatus status;
    private final String message;

    public Response(int status, String message) {
        this.status = HttpStatus.valueOf(status);
        this.message = message;
    }
}
