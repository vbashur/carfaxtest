package com.vbashur.carfax.domain;

public class ExternalErrorInfo {

    private final String url;
    private final String error;

    public ExternalErrorInfo(String url, Exception excpn) {
        this.url = url;
        this.error = excpn.getLocalizedMessage();
    }

    public String getUrl() { return url; }

    public String getError() {
        return error;
    }
}
