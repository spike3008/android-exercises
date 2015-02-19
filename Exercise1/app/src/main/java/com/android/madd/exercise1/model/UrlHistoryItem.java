package com.android.madd.exercise1.model;

import org.joda.time.DateTime;

public class UrlHistoryItem {
    private int id;
    private String url;
    private int status;
    private DateTime timeStamp;

    public UrlHistoryItem() {
    }

    public UrlHistoryItem(String url, int status, DateTime timeStamp) {
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

    public boolean isSuccessful() {
        return status == 200;
    }

    public DateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(DateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean hasEqualUrl(UrlHistoryItem urlHistoryItem) {
        return this.getUrl().equals(urlHistoryItem.getUrl());
    }

    public boolean isAfter(UrlHistoryItem urlHistoryItem) {
        return this.getTimeStamp().isAfter(urlHistoryItem.getTimeStamp());
    }
}
