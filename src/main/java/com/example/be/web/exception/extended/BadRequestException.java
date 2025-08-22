package com.example.be.web.exception.extended;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter

public class BadRequestException extends RuntimeException{

    public String message;

    public HttpStatus status;

    private String[] params;

    public BadRequestException(String message) {
        super(message);
        this.status=HttpStatus.BAD_REQUEST;
        this.message = message;
    }

    public BadRequestException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public BadRequestException(String message,String[] params) {
        super(message);
        this.status=HttpStatus.BAD_REQUEST;
        this.params = params;
        this.message = message;
    }

    public BadRequestException(String message, HttpStatus status, String[] params) {
        super(message);
        this.message = message;
        this.status = status;
        this.params = params;
    }
}
