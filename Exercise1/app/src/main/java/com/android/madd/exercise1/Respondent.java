package com.android.madd.exercise1;

import android.app.ProgressDialog;

public interface Respondent<T> {

    void addResponse(T item);

    ProgressDialog getProgressDialog();
}
