package com.uralhalil.zapu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Bir entity içerisinde kayıt olmaması durumunda gerçekleşen exception
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
    }
}
