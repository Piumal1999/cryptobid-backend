package com.cryptobid.backend.exceptions;

public class APIException extends Exception {

    public APIException() {
        super();
    }

    public APIException(String msg) {
        super(msg);
    }

    public APIException(String msg, Throwable e) {
        super(msg, e);
    }
}
