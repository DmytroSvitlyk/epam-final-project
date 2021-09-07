package com.delivery.delivery.util;

public class UtilException extends RuntimeException {

    public UtilException() {
        super();
    }

    public UtilException(String cause) {
        super(cause);
    }

    public UtilException(Throwable e) {
        super(e);
    }

    public UtilException(String cause, Throwable e) {
        super(cause, e);
    }

}
