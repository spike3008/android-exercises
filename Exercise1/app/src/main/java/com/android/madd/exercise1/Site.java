package com.android.madd.exercise1;

import org.joda.time.DateTime;

/**
 * Created by madd on 2015-01-29.
 */
public class Site {
    private int id;
    private String url;
    private int status;
    private DateTime timeStamp;

    public Site() {
    }

    public Site(String url, int status, DateTime timeStamp) {
        this.url = url;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return status==200;
    }

    public DateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(DateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
