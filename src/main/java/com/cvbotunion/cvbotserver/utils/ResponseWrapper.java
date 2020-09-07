package com.cvbotunion.cvbotserver.utils;

import java.io.Serializable;

public class ResponseWrapper implements Serializable {
    private boolean isError;
    private String simple_msg;
    private Serializable response;

    public ResponseWrapper(boolean isError, String simple_msg, Serializable response) {
        this.isError = isError;
        this.simple_msg = simple_msg;
        this.response = response;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getSimple_msg() {
        return simple_msg;
    }

    public void setSimple_msg(String simple_msg) {
        this.simple_msg = simple_msg;
    }

    public Serializable getResponse() {
        return response;
    }

    public void setResponse(Serializable response) {
        this.response = response;
    }
}
