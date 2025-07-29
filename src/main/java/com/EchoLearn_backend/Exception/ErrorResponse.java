package com.EchoLearn_backend.Exception;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private String message;

    private int status;
    private LocalDateTime timeStamp;

    public ErrorResponse( int status, String message) {
        this.message = message;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
    }

}
