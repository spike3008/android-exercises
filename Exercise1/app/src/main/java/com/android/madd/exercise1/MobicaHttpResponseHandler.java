package com.android.madd.exercise1;

import android.app.ProgressDialog;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import timber.log.Timber;

/**
 * Created by madd on 2015-01-26.
 */
class MobicaHttpResponseHandler extends AsyncHttpResponseHandler {

    private static MainActivity baseActivity;
    private final String classFullName;
    private ProgressDialog progressDialog;


    public MobicaHttpResponseHandler(Context context) {
        baseActivity = null;
        Timber.tag(this.getClass().getName());
        classFullName = "com.android.madd.exercise1.MainActivity";
        if (classFullName.equals(context.getClass().getName())) {
            baseActivity = (MainActivity) context;
            progressDialog = new ProgressDialog(baseActivity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            final CharSequence message = baseActivity.getText(R.string.dialog_wait_message);
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
        else {
            Timber.e("Invalid context!");
            Timber.e("Expected: %s", classFullName);
            Timber.e("Current: %s", context.getClass().getName());
        }


    }

    /**
     * Fired when a request returns successfully, override to handle in your own code
     *
     * @param statusCode   the status code of the response
     * @param headers      return headers, if any
     * @param responseBody the body of the HTTP response from the server
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
       Timber.i("Http response status: " + String.valueOf(statusCode));
        if (baseActivity != null) {
            progressDialog.dismiss();
            baseActivity.showToast("SUCCESS");
        }
    }

    /**
     * Fired when a request fails to complete, override to handle in your own code
     *
     * @param statusCode   return HTTP status code
     * @param headers      return headers, if any
     * @param responseBody the response body, if any
     * @param error        the underlying cause of the failure
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Timber.e("Http response status: " + String.valueOf(statusCode));
        if (baseActivity != null) {
            progressDialog.dismiss();
            baseActivity.showToast("FAILED");
        }
    }




}
