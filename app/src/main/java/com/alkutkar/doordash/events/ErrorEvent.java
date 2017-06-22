package com.alkutkar.doordash.events;

/**
 * Created by harshalkutkar on 6/22/17.
 */

public class ErrorEvent {

    int errorCode;

    public ErrorEvent(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }



}
