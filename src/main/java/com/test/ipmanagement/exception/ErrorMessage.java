package com.test.ipmanagement.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ErrorMessage {

    private String message;
    private Date timestamp;

    public ErrorMessage(String message, Date timestamp) {
        this.timestamp = timestamp;
        this.message = message;
    }
}
