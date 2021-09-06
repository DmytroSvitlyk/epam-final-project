package com.delivery.delivery.dao;

public class DAOException extends RuntimeException {

    public DAOException() {
        super();
    }

    public DAOException(String cause) {
        super(cause);
    }

    public DAOException(Throwable e) {
        super(e);
    }

    public DAOException(String cause, Throwable e) {
        super(cause, e);
    }

}
