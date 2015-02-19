package com.android.madd.exercise1.url_checking;

import android.app.ProgressDialog;

public interface Respondent<T> {

    void addResponse(T item);

    ProgressDialog getProgressDialog();
}
