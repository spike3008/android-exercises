package com.android.madd.exercise1;

import android.app.ProgressDialog;

/**
 * Created by madd on 2015-02-02.
 */
public interface Respondent<T> {
    void addResponse(T item);
    ProgressDialog getProgressDialog();
}
