package org.ais.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
