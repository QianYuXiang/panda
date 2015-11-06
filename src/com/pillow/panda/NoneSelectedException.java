package com.pillow.panda;

/**
 * Created by qianyuxiang on 15/11/5.
 */
public class NoneSelectedException extends Exception {

    public NoneSelectedException() {
        super();
    }

    public NoneSelectedException(String message) {
        super(message);
    }

    public NoneSelectedException(String message, Throwable cause) {
        super(message, cause);
    }

}
