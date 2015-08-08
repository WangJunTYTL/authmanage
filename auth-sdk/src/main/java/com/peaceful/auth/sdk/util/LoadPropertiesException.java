package com.peaceful.auth.sdk.util;

/**
 * Created by wj on 14-5-8.
 */
public class LoadPropertiesException extends RuntimeException {

    public LoadPropertiesException(String message){
        super(message);
    }

    public LoadPropertiesException(String message, Throwable cause){
        super(message,cause);
    }

}
