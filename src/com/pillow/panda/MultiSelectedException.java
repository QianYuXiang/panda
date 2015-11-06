package com.pillow.panda;

/**
 * Created by qianyuxiang on 15/11/5.
 */
public class MultiSelectedException extends Exception {
    public MultiSelectedException() {
        super();
    }

    public MultiSelectedException(String message) {
        super(message);
    }

    public MultiSelectedException(String message, Throwable cause) {
        super(message, cause);
    }
}
