package com.delivery.delivery.dao.pool;

public class PoolException extends RuntimeException {

    public PoolException() {
        super();
    }

    public PoolException(String cause) {
        super(cause);
    }

    public PoolException(Throwable e) {
        super(e);
    }

    public PoolException(String cause, Throwable e) {
        super(cause, e);
    }

}
