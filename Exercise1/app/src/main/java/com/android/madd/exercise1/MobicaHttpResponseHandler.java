package com.android.madd.exercise1;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by madd on 2015-01-26.
 */
class MobicaHttpResponseHandler extends AsyncHttpResponseHandler {

    private static MainActivity baseActivity;
    private ProgressDialog progressDialog;
    private final static String TAG = "HttpResponseHandler";


    public MobicaHttpResponseHandler(Context context) {
        baseActivity = null;
        if ("com.android.madd.exercise1.MainActivity".equals(context.getClass().getName())) {
            baseActivity = (MainActivity) context;
            progressDialog = new ProgressDialog(baseActivity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            final CharSequence message = baseActivity.getText(R.string.dialog_wait_message);
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
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
        Log.i(TAG, "Http response status: " + String.valueOf(statusCode));
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
        Log.i(TAG, "Http response status: " + String.valueOf(statusCode));
        if (baseActivity != null) {
            progressDialog.dismiss();
            baseActivity.showToast("FAILED");
        }
    }




}
