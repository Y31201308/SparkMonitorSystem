package com.wncg.news.analysis.monitor.core.matadata;

public class RequestFailedException extends RuntimeException {

    public RequestFailedException(String msg, Exception cause){
    	super(msg, cause);
    }
    
    public RequestFailedException(String msg) {
        super(msg);
    }

}
