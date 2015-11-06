package com.pillow.panda;

/**
 * Created by qianyuxiang on 15/11/5.
 */
public class UninitException extends Exception {

    public UninitException() {
        super();
    }

    public UninitException(String message) {
        super(message);
    }

    public UninitException(String message, Throwable cause) {
        super(message, cause);
    }
}
