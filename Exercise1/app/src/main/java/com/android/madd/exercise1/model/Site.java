package com.android.madd.exercise1.model;

import org.joda.time.DateTime;

/**
 * Created by madd on 2015-01-29.
 */
public class Site {
    private String url;
    private int status;
    private boolean succesful;
    private DateTime timeStamp;

    public Site(String url, int status, DateTime timeStamp) {
        this.url = url;
        this.status = status;
        this.timeStamp = timeStamp;
        this.succesful = status == 200;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccesful() {
        return succesful;
    }

    public void setSuccesful(boolean succesful) {
        this.succesful = succesful;
    }

    public DateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(DateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
