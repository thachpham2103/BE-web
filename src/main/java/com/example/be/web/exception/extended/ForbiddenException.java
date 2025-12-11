package com.example.be.web.exception.extended;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter

public class ForbiddenException extends RuntimeException{

    private String message;

    private HttpStatus status;

    private String[] params;


    public ForbiddenException(String message){
        super(message);
        this.status=HttpStatus.FORBIDDEN;
        this.message=message;
    }

    public ForbiddenException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public ForbiddenException(String message, String[] params) {
        super(message);
        this.status=HttpStatus.FORBIDDEN;
        this.message = message;
        this.params = params;
    }

    public ForbiddenException(String message, HttpStatus status, String[] params) {
        super(message);
        this.message = message;
        this.status = status;
        this.params = params;
    }
}
