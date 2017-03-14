package com.wncg.news.analysis.monitor.core.unify;

public class RequestFailedException extends RuntimeException {

    private static final long serialVersionUID = -167166478222434696L;

    public RequestFailedException(String msg, Exception cause){
        super(msg, cause);
    }

    public RequestFailedException(String msg) {
        super(msg);
    }

}
