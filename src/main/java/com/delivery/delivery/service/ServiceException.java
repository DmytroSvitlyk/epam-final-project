package com.delivery.delivery.service;

public class ServiceException extends RuntimeException {

    public ServiceException() {
        super();
    }

    public ServiceException(String cause) {
        super(cause);
    }

    public ServiceException(Throwable e) {
        super(e);
    }

    public ServiceException(String cause, Throwable e) {
        super(cause, e);
    }

}
